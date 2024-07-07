package com.example.service.Impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.exception.DataNotFoundException;
import com.example.model.Category;
import com.example.model.MultipleChoiceQuestion;
import com.example.model.SubCategory;
import com.example.repository.CategoryRepository;
import com.example.repository.MultipleChoiceQuestionRepository;
import com.example.repository.SubCategoryRepository;
import com.example.service.MultipleChoiceQuestionService;

@Service
public class MultipleChoiceQuestionServiceImpl implements MultipleChoiceQuestionService {

    public static final Logger logger = LoggerFactory.getLogger(MultipleChoiceQuestionServiceImpl.class);

    private MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
    private SubCategoryRepository subCategoryRepository;
    private CategoryRepository categoryRepository;

    public MultipleChoiceQuestionServiceImpl(MultipleChoiceQuestionRepository multipleChoiceQuestionRepository, SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository){
        this.multipleChoiceQuestionRepository = multipleChoiceQuestionRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public MultipleChoiceQuestion saveQuestion(MultipleChoiceQuestion question) {
        return multipleChoiceQuestionRepository.save(question);
    }

    @Override
    public List<MultipleChoiceQuestion> getAllQuestionsData() {
        logger.info("Fetching all MCQ Questions.");
        List<MultipleChoiceQuestion> questions = multipleChoiceQuestionRepository.findAll();
        if (questions.isEmpty()) {
            String message = "Not found any MCQ question.";
            logger.error(message);
            throw new DataNotFoundException(message);
        }
        return questions;
    }

    @Override
    public MultipleChoiceQuestion getQuestionData(Integer questionId) {
        Optional<MultipleChoiceQuestion> questionData = multipleChoiceQuestionRepository.findById(questionId);

        if (!questionData.isPresent()) {
            String message = "This question Id: "+questionId+" is not found.";
            logger.error(message);
            throw new DataNotFoundException(message);
        }
        return questionData.get();
    }

    @Override
    public MultipleChoiceQuestion updateQuestion(Integer questionId, MultipleChoiceQuestion question) {
        Optional<MultipleChoiceQuestion> existingQuestion = multipleChoiceQuestionRepository.findById(questionId);
        if (!existingQuestion.isPresent()) {
            throw new DataNotFoundException("This Question is not found with ID: " + questionId);
        }
        try {
            MultipleChoiceQuestion updateQuestionData = existingQuestion.get();

            if (question.getSubcategory() != null) {
                updateQuestionData.setSubcategory(question.getSubcategory());
            }
            if (question.getQuestion() != null) {
                updateQuestionData.setQuestion(question.getQuestion());
            }
            if (question.getOptionOne() != null) {
                updateQuestionData.setOptionOne(question.getOptionOne());
            }
            if (question.getOptionTwo() != null) {
                updateQuestionData.setOptionTwo(question.getOptionTwo());
            }
            if (question.getOptionThree() != null) {
                updateQuestionData.setOptionThree(question.getOptionThree());
            }
            if (question.getOptionFour() != null) {
                updateQuestionData.setOptionFour(question.getOptionFour());
            }
            if (question.getCorrectOption() != null) {
                updateQuestionData.setCorrectOption(question.getCorrectOption());
            }
            if (question.getPositiveMark() != null) {
                updateQuestionData.setPositiveMark(question.getPositiveMark());
            }
            if (question.getNegativeMark() != null) {
                updateQuestionData.setNegativeMark(question.getNegativeMark());
            }
            return multipleChoiceQuestionRepository.save(updateQuestionData);
        } catch (Exception e) {
            logger.error("Error updating question: {}", e.getMessage());
            throw new RuntimeException("Error updating question", e);
        }
    }

    @Override
    public void deleteQuestion(Integer questionId) {
        Optional<MultipleChoiceQuestion> question = multipleChoiceQuestionRepository.findById(questionId);
        if (!question.isPresent()) {
            String message = "This question Id: "+questionId+" is not found.";
            logger.error(message);
            throw new DataNotFoundException(message);
        }
        question.get().setSubcategory(null);
        multipleChoiceQuestionRepository.deleteById(questionId);
        logger.info("Question deleted of Id: {}", questionId);
    }

    @Override
    public void uploadBulkQuestionsFromExcelFile(MultipartFile multipartFile) {
        List<MultipleChoiceQuestion> questionsList = new ArrayList<>();
        
        try (Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = sheet.getFirstRowNum()+1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row currentRow = sheet.getRow(rowIndex);
                if (currentRow == null) {
                    continue;
                }

                MultipleChoiceQuestion mcqQuestion = new MultipleChoiceQuestion();
                Category category = null;
                SubCategory subcategory = null;
                boolean skipRow = false;

                for (int columnIndex = currentRow.getFirstCellNum(); columnIndex <= currentRow.getLastCellNum(); columnIndex++) {
                    Cell currentCell = currentRow.getCell(columnIndex);

                    switch (columnIndex) {
                        case 1:
                            category = categoryRepository.findByCategoryNameIgnoreCase(currentCell.toString())
                                    .orElse(null);
                            if (category == null) {
                                skipRow = true;
                                logger.error("Category not found: {}", currentCell.toString());
                                throw new DataNotFoundException("Given Category '"+ currentCell.toString() +"' Not present in database");
                            }
                            break;
                        case 2:
                            if (!skipRow && category != null) {
                                subcategory = subCategoryRepository.findBySubcategoryNameIgnoreCaseAndCategory(currentCell.toString(), category)
                                                            .orElse(null);
                                if (subcategory == null) {
                                    skipRow = true;
                                    logger.error("Subcategory not found: {}", currentCell.toString());
                                    throw new DataNotFoundException("Given Subcategory '"+ currentCell.toString() +"' is not present in '"+ category.getCategoryName() +"' category.");
                                }
                            }
                            break;
                        case 3:
                            if (!skipRow) {
                                mcqQuestion.setQuestion(currentCell.toString());
                            }
                            break;
                        case 4:
                            if (!skipRow) {
                                mcqQuestion.setOptionOne(currentCell.toString());
                            }
                            break;
                        case 5:
                            if (!skipRow) {
                                mcqQuestion.setOptionTwo(currentCell.toString());
                            }
                            break;
                        case 6:
                            if (!skipRow) {
                                mcqQuestion.setOptionThree(currentCell.toString());
                            }
                            break;
                        case 7:
                            if (!skipRow) {
                                mcqQuestion.setOptionFour(currentCell.toString());
                            }
                            break;
                        case 8:
                            if (!skipRow) {
                                mcqQuestion.setCorrectOption(currentCell.toString());
                            }
                            break;
                        case 9:
                            if (!skipRow) {
                                mcqQuestion.setPositiveMark(currentCell.toString());
                            }
                            break;
                        case 10:
                            if (!skipRow) {
                                mcqQuestion.setNegativeMark(currentCell.toString());
                            }
                            break;
                        default:
                            break;
                    }
                }
                if (!skipRow && subcategory != null) {
                    mcqQuestion.setSubcategory(subcategory);
                    questionsList.add(mcqQuestion);
                }
            }
            multipleChoiceQuestionRepository.saveAll(questionsList);
        }catch (IOException e){
            logger.error("Error failed to read or upload file {}: {}", multipartFile.getOriginalFilename(), e.getMessage());
        }
    }

}

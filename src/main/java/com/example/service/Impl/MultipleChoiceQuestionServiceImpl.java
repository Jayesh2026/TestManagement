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
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public MultipleChoiceQuestion saveQuestion(MultipleChoiceQuestion question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        try {
            return multipleChoiceQuestionRepository.save(question);
        } catch (Exception exception) {
            logger.error("Error while saving question: {}", exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<MultipleChoiceQuestion> getAllQuestionsData() {
        List<MultipleChoiceQuestion> questions = multipleChoiceQuestionRepository.findAll();
        if (questions == null || questions.isEmpty()) {
            throw new DataNotFoundException("Unable to retrieved questions, Not found any question.");
        }
        return questions;
    }

    @Override
    public MultipleChoiceQuestion getQuestionData(Integer questionId) {
        Optional<MultipleChoiceQuestion> questionData = multipleChoiceQuestionRepository.findById(questionId);

        if (!questionData.isPresent()) {
            throw new DataNotFoundException("This question is not present.");
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
            throw new DataNotFoundException("This question Id is not found.");
        }
        multipleChoiceQuestionRepository.deleteById(questionId);
    }

    @Override
    public void uploadBulkQuestionsFromExcelFile(MultipartFile multipartFile) {
        List<MultipleChoiceQuestion> questionsList = new ArrayList<>();
        
        try (Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = sheet.getFirstRowNum()+1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row currentRow = sheet.getRow(rowIndex);
                if (currentRow == null) {
                    continue; // Skip if row is empty
                }

                MultipleChoiceQuestion mcqQuestion = new MultipleChoiceQuestion();
                Category category = null;
                SubCategory subcategory = null;
                boolean skipRow = false;

                for (int columnIndex = currentRow.getFirstCellNum(); columnIndex <= currentRow.getLastCellNum(); columnIndex++) {
                    Cell currentCell = currentRow.getCell(columnIndex);

                    switch (columnIndex) {
                        case 1:
                            String categoryName = currentCell.getStringCellValue();
                            category = categoryRepository.findByCategoryNameIgnoreCase(categoryName)
                                    .orElse(null);
                            if (category == null) {
                                skipRow = true;
                                logger.warn("Category not found for row {}: {}", rowIndex, categoryName);
                            }
                            break;
                        case 2:
                            if (!skipRow && category != null) {
                                String subcategoryName = currentCell.getStringCellValue();
                                subcategory = subCategoryRepository
                                        .findBySubcategoryNameIgnoreCaseAndCategory(subcategoryName, category)
                                        .orElse(null);
                                if (subcategory == null) {
                                    skipRow = true;
                                    logger.warn("Subcategory not found for row {}: {}", rowIndex, subcategoryName);
                                }
                            }
                            break;
                        case 3:
                            if (!skipRow) {
                                mcqQuestion.setQuestion(currentCell.getStringCellValue());
                            }
                            break;
                        case 4:
                            if (!skipRow) {
                                mcqQuestion.setOptionOne(currentCell.getStringCellValue());
                            }
                            break;
                        case 5:
                            if (!skipRow) {
                                mcqQuestion.setOptionTwo(currentCell.getStringCellValue());
                            }
                            break;
                        case 6:
                            if (!skipRow) {
                                mcqQuestion.setOptionThree(currentCell.getStringCellValue());
                            }
                            break;
                        case 7:
                            if (!skipRow) {
                                mcqQuestion.setOptionFour(currentCell.getStringCellValue());
                            }
                            break;
                        case 8:
                            if (!skipRow) {
                                mcqQuestion.setCorrectOption(currentCell.getStringCellValue());
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

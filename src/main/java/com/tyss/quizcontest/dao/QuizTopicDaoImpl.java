package com.tyss.quizcontest.dao;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import com.tyss.quizcontest.bean.QuizQuestions;
import com.tyss.quizcontest.bean.QuizTopic;

public class QuizTopicDaoImpl implements QuizTopicDao {

	static EntityManagerFactory entityManagerFactory = null;
	static EntityManager entityManager = null;
	static EntityTransaction transaction = null;

	static void entityMethod() {
		entityManagerFactory = Persistence.createEntityManagerFactory("quizmap");
		entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public void insertTopic(QuizTopic qt) {
		try {
			entityMethod();
			transaction = entityManager.getTransaction();
			transaction.begin();
			entityManager.persist(qt);
			System.out.println("Data Inserted Successfully");
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			try {
				if (entityManagerFactory != null) {
					entityManagerFactory.close();
				}
				if (entityManager != null) {
					entityManager.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getTopic() {
		entityManagerFactory = Persistence.createEntityManagerFactory("quizmap");
		entityManager = entityManagerFactory.createEntityManager();
		String select = "from QuizTopic";
		Query query = entityManager.createQuery(select);
		List<QuizTopic> quizSub = query.getResultList();
		for (QuizTopic subject : quizSub) {
			System.out.println("Press " + subject.getTopicId() + " for " + subject.getTopicName());
		}

		try (Scanner sc = new Scanner(System.in);) {

			int opt = sc.nextInt();
			switch (opt) {
			case 1:
				List<QuizQuestions> javaQList = quizSub.get(0).getQuestion();
				playQuiz(javaQList);
				break;
			case 2:
				System.out.println("No quiz for Kotlin");
				break;
			case 3:
				System.out.println("No quiz for Python");
				break;

			default:
				System.out.println("You Have Entered Invalid Number");
			}
		}
	}

	private void playQuiz(List<QuizQuestions> quesList) {
		int questionNumber = 1;
		loadNextQuestion(questionNumber, quesList);
		try (Scanner sc = new Scanner(System.in);) {

			int opt = sc.nextInt();
			switch (opt) {
			case 1:
				questionNumber++;
				loadNextQuestion(questionNumber, quesList);
				break;
			case 2:
				questionNumber++;
				loadNextQuestion(questionNumber, quesList);
				break;
			case 3:
				questionNumber++;
				loadNextQuestion(questionNumber, quesList);
				break;
			case 4:
				questionNumber++;
				loadNextQuestion(questionNumber, quesList);
				break;

			default:
				System.out.println("You Have Entered Invalid Number");
			}
		}

	}

	private void loadNextQuestion(int questionNumber, List<QuizQuestions> quesList) {
		
		if (questionNumber == quesList.size()) {
			System.out.println("Quiz ends here Mna !!");
		}

		System.out.println("Please input the correct option number");
		System.out.println("Ques: " + questionNumber + " " + quesList.get(questionNumber).getQuestion());
		System.out.println("1." + quesList.get(questionNumber).getOpt1());
		System.out.println("2." + quesList.get(questionNumber).getOpt2());
		System.out.println("3." + quesList.get(questionNumber).getOpt2());
		System.out.println("4." + quesList.get(questionNumber).getOpt4());
		Scanner sc = new Scanner(System.in);
		int opt = sc.nextInt();
		loadNextQuestion(questionNumber, quesList);

	}
}

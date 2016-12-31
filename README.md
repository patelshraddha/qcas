# qcas
Quiz Creation and Assessment System

For this project the goal is to develop a quiz creation and assessment system (QCAS) for Carnegie Mellon University that is able to let instructors create tests for students and produce analytical charts and reports analyzing student performance. Students will also be able to us the system to practice and generate some reports on their performance.
The QCAS system essentially allows an instrutor to login and import a text file containing csv data (see sample csv file provided on BB). This csv file contains quiz questions (including code) with answers. Instructors will import quiz questions which will be added to a question bank (a relational database). Instructors will also be able to login to the system and get information about students who have been taking quizzes using the system and print reports to a pdf file. The dashboard will provide charts describing general student performance:
• Number of tests taken during the last month,last quarter and over the last year.
• Average student scores over last month, quarter and year.
• Scores by level of difficulty (each question has a difficulty level – easy, medium and hard).
• Students passing and failing over different periods.
• Any other statistic you might find interesting.
The QCAS system will also allow students to login and take quizzes. Students will have the option to select the number of questions along with the difficulty level (easy, medium, hard or mixed). The system will present quiz questions one at a time. There are four types of questions:
• Multiple choice (only 1 answer)
• Multiple answers
• True/False
• Fill in the blanks (a string) - QUESTIONS WILL ONLY HAVE 1 BLANK

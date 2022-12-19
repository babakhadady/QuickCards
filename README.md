# My Personal Project 

## What Will The Application Do?
This application is a flash card app, where a user can create flashcard sets, add new cards and use these cards to 
study with. The app is meant to be a **minimalistic and effortless experience** to creating and using flashcards. 

Users will be able to create and edit sets of flashcards, use sets and mark any flashcards that they got incorrect to 
a new set, where they can then understand their weaknesses.


- Flashcards will be double-sided with a question and answer side. 
- Ability to switch between the question and answer side of the flashcard
- Ability to move on to the next flashcard when finished



## Who Will Use It?
This application can be utilized by any age demographic trying to remember things easily and without excessive planning. 
This includes students of all ages, or even professionals.

This application is meant as a break from rigorous everyday tasks, and allows for an easy user interface that is simple 
yet effective.

## Why is This Project of Interest To You?
I have found that some other flash card websites are too complicated or require the user spend more time on the website 
than the actual reason that they are there, _to create flashcards_. Through this project I would like to create an
application that is minimalistic and easy on the eyes, but also gets to the point without wasting time. I also hope to
be able to use this application in future learning.

## User Stories

As a user, I want to be able to create a new set of flashcards with a name

As a user, I want to be able to add a new flashcard with a question and answer to a set of flashcards

As a user, I want to be able to edit a flashcard

As a user, I want to be able to use a set of flashcards

As a user, I want to be able to mark a flashcard as mastered

As a user, I want to be able to edit a flashcard set

As a user, I want to be able to delete flashcards

As a user, I want to be able to delete a flashcard set


As a user, I want the option to save the flashcard sets that I have made to file

As a user, I want the option to load my flashcard sets from file

## Phase 4: Task 2

Fri Apr 01 11:19:35 PDT 2022

Added FlashCard with question 3 + 3 to new set

Fri Apr 01 11:19:35 PDT 2022

Added FlashCard with question 2 + 2 to new set

Fri Apr 01 11:19:35 PDT 2022

Added FlashCard with question 4 + 4 to new set

Fri Apr 01 11:19:54 PDT 2022

Added FlashCard with question hello to test

Fri Apr 01 11:20:18 PDT 2022

Removed FlashCard with question hello from test

Fri Apr 01 11:20:34 PDT 2022

Removed FlashCard with question 2 + 2 from new set

## Phase 4: Task 3

The following are ways that this application could be refactored: 

- Create abstract classes to reduce duplicate code in ui classes (FlashCardApp, FlashCardSetEdit and FlashCardSetUI)
- Make the FlashCardSet class iterable
- Instead of an ArrayList of flashcards, utilize a HashSet since order does not matter
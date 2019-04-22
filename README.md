
# K-9 Mail
## SOEN 390 Project         [![Build Status](https://travis-ci.com/jeffbuonamici/k9mail.svg?token=qxHYNrXsJFtDv4Fs8FL7&branch=master)](https://travis-ci.com/jeffbuonamici/k9mail)
**Group Members:**



Daniel Stroppolo  
Jeff Buonamici    
Conor Geoghegan        
Nicolas Horta-Adam     
Paul Micu       
Youssef Akallal    
Dania Kalomiris  

**TA:** Zishuo Ding  


***
  
This project is a mini-capstone project for Software Engineering students at Concordia University. 

**Project Goal:** Clone a repository of a public open-source app, understand the app's codebase and add some simple features. 

**Project Purpose:** Learn how to work with large-scale codebases, learn how to work as a group, and practise the various aspects of software engineering (coding, testing, documentation, etc).

The app assigned to our group is **K-9 Mail**. K-9 Mail is an Android app that can collect e-mails from multiple e-mail accounts (Hotmail, GMail, etc) and present all those e-mails in a single application.


***

The official documentation for K-9 Mail can be found [here](https://github.com/k9mail/k-9/wiki). All meeting notes and documentation related to the project can be found in our [wiki](https://github.com/jeffbuonamici/k9mail/wiki)!

## CI Log Analysis

   To get a better sense of the last build that ran on Travis CI you can first:
        
* Install [npm v6.](https://www.npmjs.com/get-npm)
        
* Install [Node.js LTS v10](https://nodejs.org/en/download/)

then, from the command line, at the root of the project, run:

* ```npm install ```

* ```node .```
    
    What you will get in return is a log of the latest build, it's commit reference, the timestamp at which the build ran, the author of the commit, the status of the build, the time it took to build and the failing test if it did fail.
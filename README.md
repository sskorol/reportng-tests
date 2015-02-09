ReportNG sample
======

Implemented via maven, testng, selenium.

Main usage: pushing custom data as ReportNG output.

You can find detailed instruction here: http://qa-automation-notes.blogspot.com/2014/06/custom-reporting-engine-with-reportng.html

Project structure: 
 
 - BaseTest for WebDriver instances handling.
 - BaseHTMLReporter - custom testng listener with overridden createContext method.
 - ReportUtils - screenshot pusher.
 
 Note that this project uses pure reportng sources from maven repository. 
 For templates customization, you should pull sources, make updates and create appropriate dependency.
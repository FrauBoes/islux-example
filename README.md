# islux-example
A sample application to store and update patient data.

## Quick start
In order to develop and run the application, the following tools need to be 
installed:
1. Java 21. Can use [sdkman](https://sdkman.io/) to manage multiple Java 
versions.
2. [SceneBuilder](https://gluonhq.com/products/scene-builder/)
3. [JavaFX](https://gluonhq.com/products/javafx/)

After that, fork and clone the repository 

https://github.com/FrauBoes/islux-example

or unzip the file provided to you.

### Run application
1. Open project in IDE: Open > select project directory.
2. Run `mvn clean javafx:run` on the command line.
3. Alternatively, run `com.example.Main` in the IDE. Use the default settings.

### Run executable jar
Create an executable jar:
```
mvn clean package
```

The jar is created under `target/example-1.0-SNAPSHOT.jar`  
Run executable jar anywhere:  
```
java --module-path <path/to/javafx>/lib --add-modules javafx.controls,javafx.fxml -jar example-1.0-SNAPSHOT.jar
```
Note: On Mac, the typical path JavaFX is `/Library/Frameworks/JavaFX.framework`

### Use SceneBuilder to update sample.fxml:
Open SceneBuilder > Open Project > select sample.fxml > edit components > save

## Components
### Java Application Logic
The application offers the basic CRUD (create, read, update, delete) 
operations via the Controller.

### JavaFX Frontend (using SceneBuilder)
The frontend consists of an anchor pane with a selection of labels, buttons, 
fields, and views.

### Data 
- Model: Patient(int id, String name, LocalDate dob). The csv file has more 
fields that can be used to build out the application.
- Source: [kaggle](https://www.kaggle.com/datasets/cankatsrc/medical-records-dataset)
- Database: [SQLite](https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc)

### CSV Import
See class DataImporter.

## Further exploration
### Performance
A huge topic, so just some ideas to get you started:
- The data input is currently limited to 2000 rows. The csv file has 100.000
  rows. How can the application stay performant if all rows are read and held 
  in memory?
- Most of the CRUD operations are done on the database rather than the 
  in-memory store of the data. How can this be changed to improve the 
  performance?
- Which data structures and which algorithms might be particularly
  suitable?

### Testing
There are no tests. A big no-go! How could you verify the integrity of the 
application?
- Unit tests
- Integration tests
- Performance tests

### Database
The application uses a SQLite database with default configuration. How could
this be improved upon?

### Error Handling
The current error handling of the application is very rudimentary. How could it
be made more robust?

### User Feedback
There is only minimal feedback to the user at the moment. How the messaging to
the user be more helpful?

## Resources
Setup basic app in IntelliJ with JavaFX, SceneBuilder   
https://www.youtube.com/watch?v=IZCwawKILsk

IntelliJ Setup with JavaFX  
https://www.jetbrains.com/help/idea/javafx.html

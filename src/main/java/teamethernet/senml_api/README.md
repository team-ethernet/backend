# team-ethernet.web.senml_api
The senml app is an API for creating SenML messages in JSON or CBOR format.  
It handles the formatting of the SenML pack conforming to [RFC 8428](https://tools.ietf.org/html/rfc8428).

## Project code needs

```java
import teamethernet.senml_api.*;
``` 
### Dependencies
Jackson API for JSON and CBOR

## Use

The API is used through the methods `SenMLAPI#initJsonEncode`, `SenMLAPI#initCborEncode`, `SenMLAPI#addRecord` and `SenMLAPI#endSenML`.

The `SenMLAPI#addRecord` method takes `<Label, Object>` pairs as arguments. The supported fields are:

| Field name    | Data type |
| ------------- |:---------:|
| BASE_NAME     | String    |
| BASE_TIME     | double    |
| BASE_UNIT     | String    |
| BASE_VALUE    | double    |
| BASE_SUM      | double    |
| BASE_VERSION  | int       |
| NAME          | String    |
| UNIT          | String    |
| VALUE         | double    |
| STRING_VALUE  | String    |
| BOOLEAN_VALUE | boolean   |
| DATA_VALUE    | String    |
| SUM           | double    |
| TIME          | double    |
| UPDATE_TIME   | double    |

### Methods
```java
// Creates and begins new SenML message in JSON format
SenMLAPI.initJsonEncode();
```
```java
// Creates and begins new SenML message in CBOR format
SenMLAPI.initCborEncode();
```
```java
// Adds a record with the given fields
// For example 
// SenMLAPI#addRecord(new Pair<>(Label.BASE_NAME, "name"), new Pair<>(Label.BASE_UNIT, "unit"), new Pair<>(Label.VALUE, 4.6))
// adds a record with the fields bn = name, bu = unit, v = 4.6
SenMLAPI#addRecord(...);
```
```java
// Ends the SenML message, returns a String
SenMLAPI#endSenML();
```

## Example usage
```java
SenMLAPI senMLAPI = SenMLAPI.initJsonEncode();
senMLAPI.addRecord(new Pair<>(Label.BASE_NAME, "name"), new Pair<>(Label.BASE_UNIT, "unit"), new Pair<>(Label.VALUE, 4.6));
senMLAPI.addRecord(new Pair<>(Label.NAME, "current"), new Pair<>(Label.UNIT, "A"), new Pair<>(Label.VALUE, 1.2));
String json = senMLAPI.endSenML();

System.out.println(json);
```
Should print
```json
[{"bn":"name","bu":"unit","v":4.6},{"n":"current","u":"A","v":1.2}]
```

## Code structure
The different lables are defined in `Label.java`.  
The main code that handles the different labels is in `SenMLAPI.java`.  
Formatting is handled in `CborFormatter.java` and `JsonFormatter.java`, both implementing the functions defined in `Formatter.java`  

## Authors
Erik Flink   \
Isak Olsson \
Nelly Friman \
Anton Bothin   \
Andreas Sjödin \
Jacob Klasmark  \
Carina Wickström \
Valter Lundegårdh 

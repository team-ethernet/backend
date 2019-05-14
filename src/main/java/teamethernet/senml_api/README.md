# team-ethernet.web.senml_api
The senml app is an API for encoding and decoding SenML messages in JSON or CBOR format.  
It handles the formatting of the SenML pack conforming to [RFC 8428](https://tools.ietf.org/html/rfc8428).

## Project code needs

```java
import teamethernet.senml_api.*;
``` 
### Dependencies
Jackson API for JSON and CBOR

## Use

The API is used through the methods 

`SenMLAPI.initJsonEncode`, `SenMLAPI.initCborEncode`, `SenMLAPI#addRecord`,`SenMLAPI#getSenML` for encoding 

and 

`SenMLAPI.initJsonDecode`, `SenMLAPI.initCborDecode`, `SenMLAPI#getValue`, `SenMLAPI#getRecord`, `SenMLAPI#getLabels` for decoding

The `SenMLAPI#getValue` method takes a `Label` and an index as arguments.

The `SenMLAPI#getRecord` method takes an index as arguments.

The `SenMLAPI#getLabels` takes an index as an argument.

The `SenMLAPI#addRecord` method takes `Label.Pair ...` as arguments.

The supported labels are:

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

#### Encoding

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
SenMLAPI#getSenML();
```

#### Decoding
```java
// Specify what JSON message that you want to decode
SenMLAPI.initJsonDecode(String);
```
```java
// Specify what CBOR message that you want to decode
SenMLAPI.initCborDecode(byteArray);
```
```java
// Get the value for the given label at the given record index
// For example 
// String = SenMLAPI#getValue(Label.BASE_NAME, 0)
// Returns the base name for the first record.
SenMLAPI#getValue(Label, int);
```
```java
// Returns the record that exist at the given record index
SenMLAPI#getRecord(int);
```
```java
// Returns a List of all Labels that exist at the given record index
SenMLAPI#getLabels(int);
```

## Example usage

### Encoding
```java
SenMLAPI senMLAPI = SenMLAPI.initJsonEncode();
senMLAPI.addRecord(Label.BASE_NAME.attachValue("name"), Label.BASE_UNIT.attachValue("unit"), Label.VALUE.attachValue(4.6));
senMLAPI.addRecord(Label.NAME.attachValue("current"), Label.UNIT.attachValue("A"), Label.VALUE.attachValue(1.2));
String json = senMLAPI.getSenML();

System.out.println(json);
```
Should print
```json
[{"bn":"name","bu":"unit","v":4.6},{"n":"current","u":"A","v":1.2}]
```

```java
SenMLAPI senMLAPI = SenMLAPI.initCborEncode();
senMLAPI.addRecord(Label.BASE_NAME.attachValue("name"), Label.BASE_UNIT.attachValue("unit"), Label.VALUE.attachValue(4.6));
senMLAPI.addRecord(Label.NAME.attachValue("current"), Label.UNIT.attachValue("A"), Label.VALUE.attachValue(1.2));
String cbor = senMLAPI.getSenML();

System.out.println(cbor);
```
Should print
```cbor
82BF62626E646E616D6562627564756E69746176FB4012666666666666FFBF616E6763757272656E74617561416176FB3FF3333333333333FF
```

### Decoding
```java
String sampleJson = "[{\"bn\":\"mac:urn:dev:1234\", \"v\": 30.0}]";
SenMLAPI senMLAPI = SenMLAPI.initJsonDecode(sampleJson);
double v = senMLAPI.getValue(Label.VALUE, 0);

System.out.println(v);
```
Should print
```
30.0
```

```java
byte[] sampleCbor = new byte[]{-127, -94, 98, 98, 110, 112, 109, 97, 99, 58, 117, 114, 110, 58, 100, 101, 118, 58, 49, 50, 51, 52, 97, 118, -7, 79, -128};
SenMLAPI senMLAPI = SenMLAPI.initCborDecode(sampleCbor);
double v = senMLAPI.getValue(Label.VALUE, 0);

System.out.println(v);
```
Should print
```
30.0
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

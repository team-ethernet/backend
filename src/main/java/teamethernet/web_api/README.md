# team-ethernet.web

## Use

The API consists of different methods that can be combined with multiple optional parameters. The data is returned as arrays in JSON format with the following fields:

* **bn:** the base name of the sensor
* **u:** the unit of the measurement
* **v:** the decibel value measured
* **t:** the unix timestamp of the measurement

### Methods

The API can be accessed using different methods that each return a different set of data.

| Description   | Output format  |
|:--------------|:---------------|
|**/data**<br>Returns the average value for each selected sensor.</br> <br> </br> <br> </br>|`[`<br>`{"bn":string, "u":"dB", "v":float, "t":unix time},`</br>`{"bn":string, "u":"dB", "v":float, "t":unix time},`<br>`{"bn":string, "u":"dB", "v":float, "t":unix time},`</br>`...`<br>`]`</br>|
|**/data/min**<br>Returns the lowest measurement for each selected sensor.</br> <br> </br> <br> </br>|`[`<br>`{"bn":string, "u":"dB", "v":float, "t":unix time},`</br>`{"bn":string, "u":"dB", "v":float, "t":unix time},`<br>`{"bn":string, "u":"dB", "v":float, "t":unix time},`</br>`...`<br>`]`</br>|
|**/data/max**<br>Returns the highest measurement for each selected sensor.</br> <br> </br> <br> </br>|`[`<br>`{"bn":string, "u":"dB", "v":float, "t":unix time},`</br>`{"bn":string, "u":"dB", "v":float, "t":unix time},`<br>`{"bn":string, "u":"dB", "v":float, "t":unix time},`</br>`...`<br>`]`</br>|
|**/data/average**<br>Returns the average value for each selected sensor</br> <br> </br> <br> </br>|`[`<br>`{"bn":string, "u":"dB", "v":float},`</br>`{"bn":String, "u":"dB", "v":float},`<br>`{"bn":String, "u":"dB", "v":float},`</br>`...`<br>`]`</br>|

### Parameters

A selection of sensor data can be specified by typing a list of the following parameters in the URL as a query string. The order of the parameters does not matter.

| Description   | Input format  | Default value  |
|:--------------|:--------------|:---------------|
|**ids**<br>Returns data only from the specified sensor.</br>&nbsp;|`ids=id0,id1,...`<br>or</br>`ids=id0&ids=id1&ids=...`|All sensor ids<br> </br>&nbsp;|
|**startDate**<br>Returns data from the start date and forward.</br>|`startDate=xxxxxxxxxxxxx` (unix time)|0<br> </br>|
|**endDate**<br>Returns data up until the end date.</br>|`endDate=xxxxxxxxxxxxx` (unix time)|Current date<br> </br>|
|**sortBy**<br>Returns values sorted by selected field.</br> <br> </br>|`sortBy=t`,<br>`sortBy=v`</br>or<br>`sortBy=bn`</br>|t (sorted by date)<br> </br> <br> </br>|
|**sortOrder**<br>Returns sorted values in ascending or descending order.</br>&nbsp;|`sortOrder=desc`<br>or</br>`sortOrder=asc`|desc (Descending order)<br></br>&nbsp;|
|**minNoiseLevel**<br>Returns data where the noise level (v) is above minNoiseLevel</br>|`minNoiseLevel=x`<br> </br>&nbsp;|0<br> </br>&nbsp;|
|**maxNoiseLevel**<br>Returns data where the noise level (v) is below maxNoiseLevel.</br>|`maxNoiseLevel=x`<br> </br>&nbsp;|200<br> </br>&nbsp;|
|**limit**<br>The number of measurements to be returned</br>|`limit=x`<br> </br>|Unlimited<br> </br>|
|**standardDeviationFilter**<br>Returns all data within the specified standard deviation</br>|`standardDeviationFilter=x`<br> </br>|Unlimited<br> </br>|

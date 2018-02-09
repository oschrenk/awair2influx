# awair2influxdb

Takes csv files and converts them to line protocol

```
UTC_Time,Local_Time,Temperature_C,Temperature_F,Relative_Humidity,Carbon_Dioxide,Chemicals,Dust
2017-12-21 15:15:00,2017-12-21 16:15:00,17.54,63.57,76.39,777.0,359.0,27.3
2017-12-21 15:30:00,2017-12-21 16:30:00,20.07,68.13,63.74,932.0,265.0,18.6
```

```
temperature,location=livingroom value=17.54 1513869300000000
humidity,location=livingroom value=76.39 1513869300000000
carbon_dioxide,location=livingroom value=777.0 1513869300000000
chemicals,location=livingroom value=359.0 1513869300000000
dust,location=livingroom value=27.3 1513869300000000
```

## Usage

```
awair2influxdb --location livingroom input.csv

# will create livingroom.txt
```

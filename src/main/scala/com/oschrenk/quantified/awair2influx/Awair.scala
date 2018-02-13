package com.oschrenk.quantified.awair2influx

import java.time.{LocalDateTime, ZoneOffset}
import java.time.format.DateTimeFormatter

import com.oschrenk.quantified.awair2influx.ui.{CliParser, Options}

//UTC_Time,Local_Time,Temperature_C,Temperature_F,Relative_Humidity,Carbon_Dioxide,Chemicals,Dust
//2017-12-21 15:15:00,2017-12-21 16:15:00,17.54,63.57,76.39,777.0,359.0,27.3
//2017-12-21 15:30:00,2017-12-21 16:30:00,20.07,68.13,63.74,932.0,265.0,18.6
case class Measurement(utcTime: LocalDateTime, localTime: LocalDateTime, celsius: Float, fahrenheit: Float, humidity: Float, co2: Float, chemicals: Float, dust: Float)

object Awair {

  private val BastardIsoFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  def main(args: Array[String]): Unit = {

    new CliParser().parser.parse(args, Options.default) match {
      case Some(Options(location, paths)) =>
        import kantan.csv._
        import kantan.csv.ops._
        import kantan.csv.java8._
        // intellij would tell you that it's not needed, but it's a lie, a lie I tell you
        import kantan.csv.generic._

        implicit val decoder: CellDecoder[LocalDateTime] = localDateTimeDecoder(BastardIsoFormat)

        val measurements = paths.head.unsafeReadCsv[List, Measurement](rfc.withHeader)

        // temperature,location=livingroom value=17.54 1513869300000000000
        // humidity,location=livingroom value=76.39 1513869300000000000
        // carbon_dioxide,location=livingroom value=777.0 1513869300000000000
        // chemicals,location=livingroom value=359.0 1513869300000000000
        // dust,location=livingroom value=27.3 1513869300000000000
        def print(field: String, location: Option[String], value: Float, timestamp: LocalDateTime): String = {
          val tag = location match {
            case Some(l) => s",location=$l"
            case None => ""
          }
          s"$field$tag value=$value ${timestamp.toEpochSecond(ZoneOffset.UTC) * 1000 * 1000 * 1000}"
        }

        measurements.foreach{ measurement =>
          println(print("temperature", location, measurement.celsius, measurement.utcTime ))
          println(print("humidity", location, measurement.humidity, measurement.utcTime ))
          println(print("carbon_dioxide", location, measurement.co2, measurement.utcTime ))
          println(print("chemicals", location, measurement.chemicals, measurement.utcTime ))
          println(print("dust", location, measurement.dust, measurement.utcTime ))
        }

      case _ =>
        println("error parsing")
    }
  }
}

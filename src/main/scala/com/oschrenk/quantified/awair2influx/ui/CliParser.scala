package com.oschrenk.quantified.awair2influx.ui

import java.io.File

import scopt.OptionParser

object Options {
  def default: Options = Options(None, None)
}
case class Options(location: Option[String], path: Option[File])

class CliParser() {

  val parser: OptionParser[Options] = new OptionParser[Options]("awair2influx") {
    head("awair2influx")

    opt[String]('l', "location")
      .action{(location, o) =>
        o.copy(location = Some(location))
      }
  }
}

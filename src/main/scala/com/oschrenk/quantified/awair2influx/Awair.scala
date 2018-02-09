package com.oschrenk.quantified.awair2influx

import com.oschrenk.quantified.awair2influx.ui.{CliParser, Options}

object Awair extends App {

  new CliParser().parser.parse(args, Options.default) match {
    case Some(Options(location, path)) =>
      println(s"yeah $location $path")
    case _ =>
      println("error parsing")
  }
}

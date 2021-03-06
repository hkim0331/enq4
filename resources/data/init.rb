#!/usr/bin/env ruby -Ku
#-*- coding: utf-8 -*-
require 'sequel'

MY_DEBUG = false

DB = Sequel.sqlite(ARGV[0])
DS = DB[:enq4]


original="o/h26_syllabus.doc"
while (line=STDIN.gets)
  puts line if MY_DEBUG
  name,subject,q1,q2,q3,q4=line.chomp.split(/,/)
  DS.insert(:name => name, :subject => subject,
            :original => original,
            :q1 => q1, :q2 => q2, :q3 => q3, :q4 => q4)
end




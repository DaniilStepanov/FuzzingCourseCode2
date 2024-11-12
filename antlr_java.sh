#!/bin/bash

generate() {
  java -jar libs/antlr-4.13.1-complete.jar -o /home/zver/IdeaProjects/FuzzingCourseCode2/src/main/java/org/itmo/fuzzing/lect9/parser -Dlanguage=Java -package org.itmo.fuzzing.lect9.parser antlr/*.g4
  #cp /home/zver/IdeaProjects/psi-fuzz/templates-db/src/generated/com/spbpu/bbfinfrastructure/template/parser/TemplateLexer.tokens /home/zver/IdeaProjects/psi-fuzz/templates-db
}

case $1 in
  --generate)
    generate
    ;;
  --clean)
    clean
    ;;
  *)
    echo "FIX ERROR DESCRIPTION"
    ;;
esac

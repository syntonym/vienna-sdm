:PHONY: run clean

%.class: %.java
	javac $<

run: main.jar
	java -jar main.jar

clean:
	rm *.jar
	rm *.class

main.jar: Main.class Point.class
	jar cef Main main.jar *.class

all: main.jar

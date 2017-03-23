:PHONY: run clean

%.class: %.java
	javac -cp "core.jar:." $<

run: main.jar
	java -cp "core.jar:." -jar main.jar

clean:
	rm main.jar
	rm *.class

main.jar: Main.class Point.class
	jar cef Main main.jar *.class

all: main.jar

all:
	mkdir -p classes
	find ./src -name "*.java" > sources.txt
	javac -d classes @sources.txt
	rm sources.txt

clean:
	rm -rf classes/

make re:
	make clean
	make

run:
	java -cp classes src.main.java.VMTranslator.VMTranslator 

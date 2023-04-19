# Makefile for Filters
# RGRTAY001
# Tayla Rogers
# 13 August 2022

.SUFFIXES: .java .class

SRCDIR = src/typingTutor
BINDIR = bin

JAVAC = /usr/bin/javac
JAVA = /usr/bin/java

$(BINDIR)/%.class: $(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES = WordDictionary.class Score.class ScoreUpdater.Class FallingWord.class CatchWord.class GamePanel.class WordMover.class HungryWordMover.class TypingTutorApp.class

CLASS_FILES = $(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/typingTutor/*.class

run: $(CLASS_FILES)
	java -cp $(BINDIR) typingTutor.TypingTutorApp 30 5
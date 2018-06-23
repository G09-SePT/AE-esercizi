# si fa uso del flag -ftrapv per contrastare il flag -fstrict-overflow
# quest'ultimo, per ottimizzare il codice, fa si che il compilatore
# assume che gli overflow non accadono
CFLAGS=-Wall -pedantic -std=c99 -O2 -g -ftrapv
LFLAGS=-lm

EXECS=mal-test

tutti: $(EXECS)

# nelle regole di compilazione:
#	$@ viene sostituito con il nome del target
#	$^ viene sostituito con tutte le dipendenze
# 	$< viene sostituito con la prima dipendenza

%: %.c
	$(CC) $(CFLAGS) -o $@ $^ $(LFLAGS)

clean:
	$(RM) $(EXECS)

.PHONY: clean

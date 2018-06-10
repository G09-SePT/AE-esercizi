CFLAGS=-Wall -pedantic -std=c99 -O2 -g
LFLAGS=-lm
EXECS=gen_code

tutti: $(EXECS)

%: %.c
	$(CC) $(CFLAGS) -o $@ $^ $(LFLAGS)

clean:
	$(RM) $(EXECS)

.PHONY: clean
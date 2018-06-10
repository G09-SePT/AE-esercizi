#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s string\n", argv[0]);
        return 1;
    }
    fprintf(stdout, "%s\n", ".main");
    for (int i = 0; i < strlen(argv[1]); ++i) {
        if (argv[1][i] < 0 || argv[1][i] > 127) {
            fprintf(stderr, "%c char not valid\n", argv[1][i]);
            return 1;
        }
        fprintf(stdout, "\t%s 0x%x\t//%c\n", "BIPUSH", argv[1][i], argv[1][i]);
        fprintf(stdout, "\t%s\n", "OUT");
    }
    fprintf(stdout, "%s\n", ".end-main");
    return 0;
}


#define _GNU_SOURCE

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <math.h>

#define BITS 16

bool confronta(int tos, int mdr) {
    if (mdr < 0) /* equivale a: if_icmplt6  N = OPC; if (N) goto NEG; else goto POS*/
        goto neg;
    else
        goto pos;

    pos: /* equivale a: POS   N = H; if (N) goto OVERFLOW; else goto NO_OVERFLOW */
    if (tos < 0)
        goto overflow;
    else
        goto no_overflow;

    neg: /* equivale a: N = NOT H; if (N) goto OVERFLOW; else goto NO_OVERFLOW */
    if (~tos < 0)
        goto overflow;
    else
        goto no_overflow;

    overflow:
    return mdr < 0 ? true : false; /* equivale a: OVERFLOW N = OPC; if (N) goto T; else goto F */

    no_overflow:
    return mdr - tos < 0 ? true : false; /* equivale a: NO_OVERFLOW N = OPC - H; if (N) goto T; else goto F */
}

bool cmp(int tos, int mdr) {
    if ((tos ^ mdr) < 0) /* equivale alle labels: if_icmplt6, POS, NEG */
        return mdr < 0; /* equivale a: OVERFLOW N = OPC; if (N) goto T; else goto F */
    return mdr - tos < 0; /* equivale a: NO_OVERFLOW N = OPC - H; if (N) goto T; else goto F */
}

int main(int argc, char *argv[]) {
    /* Di seguito vengono effettuati alcuni test con valori particolari
     * i quali generano overflow.
     * NB: il seguente programma C si comporta come il simulatore di
     * Mic-1 */
    fprintf(stdout, "%s", "Test dei casi particolari: ");
    int tos[] = {(int) (pow(2, 32 - 1) - 1), (int) -pow(2, 32 - 1), 0, -1, 1};
    int mdr[] = {-1, (int) (pow(2, 32 - 1) - 1), 0, (int) -pow(2, 32 - 1), 1};
    for (int i = 0; i < 5; ++i) {
        for (int j = 0; j < 5; ++j) {
            if (confronta(tos[j], mdr[i]) != (mdr[i] < tos[j])) {
                fprintf(stderr, "ERROR: tos=%011d mdr=%011d mdr<tos\n", tos[i], mdr[j]);
                exit(EXIT_FAILURE);
            }
        }
    }
    fprintf(stdout, "%s\n", "FATTO");
#if 1
    for (int tos = pow(2, BITS - 1) - 1; tos >= -pow(2, BITS - 1); --tos) {
        for (int mdr = -pow(2, BITS - 1); mdr <= pow(2, BITS - 1) - 1; ++mdr) {
            if (confronta(tos, mdr) != (mdr < tos)) {
                fprintf(stderr, "ERROR: tos=%011d mdr=%011d mdr<tos\n", tos, mdr);
                exit(EXIT_FAILURE);
            }
        }
    }
#endif
    return 0;
}

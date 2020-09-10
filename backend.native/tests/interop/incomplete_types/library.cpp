#include "library.h"

extern "C" {

struct S {
    const char* name;
};

struct S s;

void setContent(struct S* s, const char* name) {
    s->name = name;
}

const char* getContent(struct S* s) {
    return s->name;
}

union U {
    float f;
    double d;
};

void setDouble(union U* u, double value) {
    u->d = value;
}

double getDouble(union U* u) {
    return u->d;
}

union U u;

char array[5];

void setArrayValue(char* array, char value) {
    for (int i = 0; i < 5; ++i) {
        array[i] = value;
    }
}

int arrayLength() {
    return sizeof(array) / sizeof(char);
}

}
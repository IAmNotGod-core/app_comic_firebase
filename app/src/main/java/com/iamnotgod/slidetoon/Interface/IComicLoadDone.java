package com.iamnotgod.slidetoon.Interface;

import com.iamnotgod.slidetoon.Model.Comic;

import java.util.List;

public interface IComicLoadDone {
    void onComicLoadDone(List<Comic> comics);
}

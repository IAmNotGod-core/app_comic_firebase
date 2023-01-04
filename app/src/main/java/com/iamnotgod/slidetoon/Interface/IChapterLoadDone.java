package com.iamnotgod.slidetoon.Interface;

import com.iamnotgod.slidetoon.Model.Chapter;

import java.util.List;

public interface IChapterLoadDone {
    void onChapterLoad(List<Chapter> chapterList);
}

package com.fsck.k9.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LabelPageTest {

    private Button button;
    private EditText editText = mock(EditText.class);
    private LabelPageLogic lpl = spy(new LabelPageLogic());

    private LabelPage lp;
    private View view;

    @Before
    public void setUp() {
        view = mock(View.class);
        lp = mock(LabelPage.class);
        when(view.getId()).thenReturn(123);
    }

    @Test
    public void onClickLogicTest() {
        Mockito.doReturn("").when(lpl).getLabelString(lp);
        lpl.onClickLogic(view, lp);
        verify(lpl, never()).setLabel(lp);
    }

    @Test
    public void onClickLogicTestWithLabel() {
        Mockito.doReturn("label").when(lpl).getLabelString(lp);
        lpl.onClickLogic(view, lp);
        verify(lpl, atLeastOnce()).setLabel(lp);
    }
}

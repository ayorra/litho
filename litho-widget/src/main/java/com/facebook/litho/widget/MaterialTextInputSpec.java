/*
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.litho.widget;

import static com.facebook.litho.widget.TextInputSpec.EditTextWithEventHandlers;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.MovementMethod;
import android.view.KeyEvent;
import android.widget.EditText;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.Diff;
import com.facebook.litho.Output;
import com.facebook.litho.Size;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.FromTrigger;
import com.facebook.litho.annotations.MountSpec;
import com.facebook.litho.annotations.OnBind;
import com.facebook.litho.annotations.OnCreateInitialState;
import com.facebook.litho.annotations.OnCreateMountContent;
import com.facebook.litho.annotations.OnLoadStyle;
import com.facebook.litho.annotations.OnMeasure;
import com.facebook.litho.annotations.OnMount;
import com.facebook.litho.annotations.OnTrigger;
import com.facebook.litho.annotations.OnUnbind;
import com.facebook.litho.annotations.OnUnmount;
import com.facebook.litho.annotations.OnUpdateState;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.annotations.ShouldUpdate;
import com.facebook.litho.annotations.State;
import com.facebook.litho.utils.MeasureUtils;
import com.google.android.material.textfield.TextInputLayout;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

/**
 * Component that renders an editable text input with a floating label when the hint is hidden while
 * the user inputs text, using an android {@link TextInput} wrapped in a {@link TextInputLayout}.
 *
 * @see {@link TextInput} for usage instructions
 */
@MountSpec(
    isPureRender = true,
    events = {
      TextChangedEvent.class,
      SelectionChangedEvent.class,
      KeyUpEvent.class,
      KeyPreImeEvent.class,
      EditorActionEvent.class,
      SetTextEvent.class,
      InputConnectionEvent.class,
    })
class MaterialTextInputSpec {

  @PropDefault
  protected static final ColorStateList textColorStateList = TextInputSpec.textColorStateList;

  @PropDefault
  protected static final ColorStateList hintColorStateList = TextInputSpec.hintColorStateList;

  @PropDefault static final CharSequence hint = TextInputSpec.hint;
  @PropDefault static final CharSequence initialText = TextInputSpec.initialText;
  @PropDefault protected static final int shadowColor = TextInputSpec.shadowColor;
  @PropDefault protected static final int textSize = TextInputSpec.textSize;
  @PropDefault protected static final Drawable inputBackground = TextInputSpec.inputBackground;
  @PropDefault protected static final Typeface typeface = TextInputSpec.typeface;
  @PropDefault protected static final int textAlignment = TextInputSpec.textAlignment;
  @PropDefault protected static final int gravity = TextInputSpec.gravity;
  @PropDefault protected static final boolean editable = TextInputSpec.editable;
  @PropDefault protected static final int inputType = TextInputSpec.inputType;
  @PropDefault protected static final int imeOptions = TextInputSpec.imeOptions;
  @PropDefault protected static final int cursorDrawableRes = TextInputSpec.cursorDrawableRes;
  @PropDefault static final boolean multiline = TextInputSpec.multiline;
  @PropDefault protected static final int minLines = TextInputSpec.minLines;
  @PropDefault protected static final int maxLines = TextInputSpec.maxLines;
  @PropDefault protected static final MovementMethod movementMethod = TextInputSpec.movementMethod;

  @OnCreateInitialState
  static void onCreateInitialState(
      final ComponentContext c,
      StateValue<AtomicReference<EditTextWithEventHandlers>> mountedEditTextRef,
      StateValue<AtomicReference<CharSequence>> savedText,
      StateValue<Integer> measureSeqNumber,
      @Prop(optional = true, resType = ResType.STRING) CharSequence initialText) {
    TextInputSpec.onCreateInitialState(
        c, mountedEditTextRef, savedText, measureSeqNumber, initialText);
  }

  @OnLoadStyle
  static void onLoadStyle(ComponentContext c, Output<Integer> highlightColor) {
    TextInputSpec.onLoadStyle(c, highlightColor);
  }

  @OnMeasure
  static void onMeasure(
      ComponentContext c,
      ComponentLayout layout,
      int widthSpec,
      int heightSpec,
      Size size,
      @Prop(optional = true, resType = ResType.STRING) CharSequence hint,
      @Prop(optional = true, resType = ResType.DRAWABLE) Drawable inputBackground,
      @Prop(optional = true, resType = ResType.DIMEN_OFFSET) float shadowRadius,
      @Prop(optional = true, resType = ResType.DIMEN_OFFSET) float shadowDx,
      @Prop(optional = true, resType = ResType.DIMEN_OFFSET) float shadowDy,
      @Prop(optional = true, resType = ResType.COLOR) int shadowColor,
      @Prop(optional = true) ColorStateList textColorStateList,
      @Prop(optional = true) ColorStateList hintColorStateList,
      @Prop(optional = true, resType = ResType.COLOR) Integer highlightColor,
      @Prop(optional = true, resType = ResType.DIMEN_TEXT) int textSize,
      @Prop(optional = true) Typeface typeface,
      @Prop(optional = true) int textAlignment,
      @Prop(optional = true) int gravity,
      @Prop(optional = true) boolean editable,
      @Prop(optional = true) int inputType,
      @Prop(optional = true) int imeOptions,
      @Prop(optional = true, varArg = "inputFilter") List<InputFilter> inputFilters,
      @Prop(optional = true) boolean multiline,
      @Prop(optional = true) TextUtils.TruncateAt ellipsize,
      @Prop(optional = true) int minLines,
      @Prop(optional = true) int maxLines,
      @Prop(optional = true) int cursorDrawableRes,
      @Prop(optional = true, resType = ResType.STRING) CharSequence error,
      @Prop(optional = true, resType = ResType.DRAWABLE) Drawable errorDrawable,
      @State AtomicReference<CharSequence> savedText) {
    EditText editText =
        TextInputSpec.createAndMeasureEditText(
            c,
            layout,
            widthSpec,
            heightSpec,
            size,
            null,
            inputBackground,
            shadowRadius,
            shadowDx,
            shadowDy,
            shadowColor,
            textColorStateList,
            hintColorStateList,
            highlightColor,
            textSize,
            typeface,
            textAlignment,
            gravity,
            editable,
            inputType,
            imeOptions,
            inputFilters,
            multiline,
            ellipsize,
            minLines,
            maxLines,
            cursorDrawableRes,
            error,
            errorDrawable,
            savedText.get());

    TextInputLayout textInputLayout = new TextInputLayout(c.getAndroidContext());
    textInputLayout.addView(editText);
    textInputLayout.setHint(hint);
    textInputLayout.measure(
        MeasureUtils.getViewMeasureSpec(widthSpec), MeasureUtils.getViewMeasureSpec(heightSpec));

    TextInputSpec.setSizeForView(size, widthSpec, heightSpec, textInputLayout);
  }

  @ShouldUpdate
  static boolean shouldUpdate(
      @Prop(optional = true, resType = ResType.STRING) Diff<CharSequence> initialText,
      @Prop(optional = true, resType = ResType.STRING) Diff<CharSequence> hint,
      @Prop(optional = true, resType = ResType.DRAWABLE) Diff<Drawable> inputBackground,
      @Prop(optional = true, resType = ResType.DIMEN_OFFSET) Diff<Float> shadowRadius,
      @Prop(optional = true, resType = ResType.DIMEN_OFFSET) Diff<Float> shadowDx,
      @Prop(optional = true, resType = ResType.DIMEN_OFFSET) Diff<Float> shadowDy,
      @Prop(optional = true, resType = ResType.COLOR) Diff<Integer> shadowColor,
      @Prop(optional = true) Diff<ColorStateList> textColorStateList,
      @Prop(optional = true) Diff<ColorStateList> hintColorStateList,
      @Prop(optional = true, resType = ResType.COLOR) Diff<Integer> highlightColor,
      @Prop(optional = true, resType = ResType.DIMEN_TEXT) Diff<Integer> textSize,
      @Prop(optional = true) Diff<Typeface> typeface,
      @Prop(optional = true) Diff<Integer> textAlignment,
      @Prop(optional = true) Diff<Integer> gravity,
      @Prop(optional = true) Diff<Boolean> editable,
      @Prop(optional = true) Diff<Integer> inputType,
      @Prop(optional = true) Diff<Integer> imeOptions,
      @Prop(optional = true, varArg = "inputFilter") Diff<List<InputFilter>> inputFilters,
      @Prop(optional = true) Diff<TextUtils.TruncateAt> ellipsize,
      @Prop(optional = true) Diff<Boolean> multiline,
      @Prop(optional = true) Diff<Integer> minLines,
      @Prop(optional = true) Diff<Integer> maxLines,
      @Prop(optional = true) Diff<Integer> cursorDrawableRes,
      @Prop(optional = true) Diff<MovementMethod> movementMethod,
      @Prop(optional = true, resType = ResType.STRING) Diff<CharSequence> error,
      @State Diff<Integer> measureSeqNumber,
      @State Diff<AtomicReference<EditTextWithEventHandlers>> mountedEditTextRef,
      @State Diff<AtomicReference<CharSequence>> savedText) {
    return TextInputSpec.shouldUpdate(
        initialText,
        hint,
        inputBackground,
        shadowRadius,
        shadowDx,
        shadowDy,
        shadowColor,
        textColorStateList,
        hintColorStateList,
        highlightColor,
        textSize,
        typeface,
        textAlignment,
        gravity,
        editable,
        inputType,
        imeOptions,
        inputFilters,
        ellipsize,
        multiline,
        minLines,
        maxLines,
        cursorDrawableRes,
        movementMethod,
        error,
        measureSeqNumber,
        mountedEditTextRef,
        savedText);
  }

  @OnCreateMountContent
  protected static TextInputLayout onCreateMountContent(Context c) {
    EditTextWithEventHandlers editText = new EditTextWithEventHandlers(c);
    TextInputLayout textInputLayout = new TextInputLayout(c);
    textInputLayout.addView(editText);
    return textInputLayout;
  }

  @OnMount
  static void onMount(
      final ComponentContext c,
      TextInputLayout textInputLayout,
      @Prop(optional = true, resType = ResType.STRING) CharSequence hint,
      @Prop(optional = true, resType = ResType.DRAWABLE) Drawable inputBackground,
      @Prop(optional = true, resType = ResType.DIMEN_OFFSET) float shadowRadius,
      @Prop(optional = true, resType = ResType.DIMEN_OFFSET) float shadowDx,
      @Prop(optional = true, resType = ResType.DIMEN_OFFSET) float shadowDy,
      @Prop(optional = true, resType = ResType.COLOR) int shadowColor,
      @Prop(optional = true) ColorStateList textColorStateList,
      @Prop(optional = true) ColorStateList hintColorStateList,
      @Prop(optional = true, resType = ResType.COLOR) Integer highlightColor,
      @Prop(optional = true, resType = ResType.DIMEN_TEXT) int textSize,
      @Prop(optional = true) Typeface typeface,
      @Prop(optional = true) int textAlignment,
      @Prop(optional = true) int gravity,
      @Prop(optional = true) boolean editable,
      @Prop(optional = true) int inputType,
      @Prop(optional = true) int imeOptions,
      @Prop(optional = true, varArg = "inputFilter") List<InputFilter> inputFilters,
      @Prop(optional = true) boolean multiline,
      @Prop(optional = true) int minLines,
      @Prop(optional = true) int maxLines,
      @Prop(optional = true) TextUtils.TruncateAt ellipsize,
      @Prop(optional = true) int cursorDrawableRes,
      @Prop(optional = true) MovementMethod movementMethod,
      @Prop(optional = true, resType = ResType.STRING) CharSequence error,
      @Prop(optional = true, resType = ResType.DRAWABLE) Drawable errorDrawable,
      @State AtomicReference<CharSequence> savedText,
      @State AtomicReference<EditTextWithEventHandlers> mountedEditTextRef) {
    final EditTextWithEventHandlers editText =
        (EditTextWithEventHandlers) textInputLayout.getEditText();
    mountedEditTextRef.set(editText);

    TextInputSpec.setParams(
        editText,
        null,
        TextInputSpec.getBackgroundOrDefault(c, inputBackground),
        shadowRadius,
        shadowDx,
        shadowDy,
        shadowColor,
        textColorStateList,
        hintColorStateList,
        highlightColor,
        textSize,
        typeface,
        textAlignment,
        gravity,
        editable,
        inputType,
        imeOptions,
        inputFilters,
        multiline,
        ellipsize,
        minLines,
        maxLines,
        cursorDrawableRes,
        movementMethod,
        // onMount happens:
        // 1. After initState: savedText = initText.
        // 2. After onUnmount: savedText preserved from underlying editText.
        savedText.get(),
        error,
        errorDrawable);
    textInputLayout.setHint(hint);
    editText.setTextState(savedText);
  }

  @OnBind
  static void onBind(
      final ComponentContext c,
      TextInputLayout textInputLayout,
      @Prop(optional = true, varArg = "textWatcher") List<TextWatcher> textWatchers) {
    final EditTextWithEventHandlers editText =
        (EditTextWithEventHandlers) textInputLayout.getEditText();
    TextInputSpec.onBindEditText(
        c,
        editText,
        textWatchers,
        MaterialTextInput.getTextChangedEventHandler(c),
        MaterialTextInput.getSelectionChangedEventHandler(c),
        MaterialTextInput.getKeyUpEventHandler(c),
        MaterialTextInput.getKeyPreImeEventHandler(c),
        MaterialTextInput.getEditorActionEventHandler(c),
        MaterialTextInput.getInputConnectionEventHandler(c));
  }

  @OnUnmount
  static void onUnmount(
      ComponentContext c,
      TextInputLayout textInputLayout,
      @State AtomicReference<EditTextWithEventHandlers> mountedEditTextRef) {
    final EditTextWithEventHandlers editText =
        (EditTextWithEventHandlers) textInputLayout.getEditText();
    TextInputSpec.onUnmount(c, editText, mountedEditTextRef);
  }

  @OnUnbind
  static void onUnbind(final ComponentContext c, TextInputLayout textInputLayout) {
    final EditTextWithEventHandlers editText =
        (EditTextWithEventHandlers) textInputLayout.getEditText();
    TextInputSpec.onUnbind(c, editText);
  }

  @OnTrigger(RequestFocusEvent.class)
  static void requestFocus(
      ComponentContext c, @State AtomicReference<EditTextWithEventHandlers> mountedEditTextRef) {
    TextInputSpec.requestFocus(c, mountedEditTextRef);
  }

  @OnTrigger(ClearFocusEvent.class)
  static void clearFocus(
      ComponentContext c, @State AtomicReference<EditTextWithEventHandlers> mountedEditTextRef) {
    TextInputSpec.clearFocus(c, mountedEditTextRef);
  }

  @OnTrigger(GetTextEvent.class)
  @Nullable
  static CharSequence getText(
      ComponentContext c,
      @State AtomicReference<EditTextWithEventHandlers> mountedEditTextRef,
      @State AtomicReference<CharSequence> savedText) {
    return TextInputSpec.getText(c, mountedEditTextRef, savedText);
  }

  @OnTrigger(SetTextEvent.class)
  static void setText(
      ComponentContext c,
      @State AtomicReference<EditTextWithEventHandlers> mountedEditTextRef,
      @State AtomicReference<CharSequence> savedText,
      @FromTrigger CharSequence text) {
    boolean shouldRemeasure = TextInputSpec.setTextEditText(mountedEditTextRef, savedText, text);
    if (shouldRemeasure) {
      MaterialTextInput.remeasureForUpdatedTextSync(c);
    }
  }

  @OnTrigger(DispatchKeyEvent.class)
  static void dispatchKey(
      ComponentContext c,
      @State AtomicReference<EditTextWithEventHandlers> mountedEditTextRef,
      @FromTrigger KeyEvent keyEvent) {
    TextInputSpec.dispatchKey(c, mountedEditTextRef, keyEvent);
  }

  @OnTrigger(SetSelectionEvent.class)
  static void setSelection(
      ComponentContext c,
      @State AtomicReference<EditTextWithEventHandlers> mountedEditTextRef,
      @FromTrigger int start,
      @FromTrigger int end) {
    TextInputSpec.setSelection(c, mountedEditTextRef, start, end);
  }

  @OnUpdateState
  static void remeasureForUpdatedText(StateValue<Integer> measureSeqNumber) {
    measureSeqNumber.set(measureSeqNumber.get() + 1);
  }
}

package com.hichemtech.android_utils;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.hichemtech.android_utils.TextChangedListener;

public class Forms {
    private EditText[] editTexts;
    private final Context context;

    public Forms(Context context) {
        this.context = context;
    }

    public Boolean ifEmptySetError(EditText[] editTexts, Object lengthLimit) {
        boolean ready = true;
        for (int i = 0; i < editTexts.length; i++) {
            EditText editText = editTexts[i];
            if (editText != null && editText.getText() != null) {
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    try {
                        ((TextInputLayout)editText.getParent().getParent()).setError(context.getString(R.string.errorText_requiredField));
                        try {
                            editText.addTextChangedListener(new TextChangedListener<EditText>(editText) {
                                @Override
                                public void onTextChanged(EditText target, Editable s) {
                                    ((TextInputLayout)target.getParent().getParent()).setError(null);
                                }
                            });
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }

                    } catch (Exception e) {
                        try {
                            editText.setError(context.getString(R.string.errorText_requiredField));

                            editText.addTextChangedListener(new TextChangedListener<EditText>(editText) {
                                @Override
                                public void onTextChanged(EditText target, Editable s) {
                                    target.setError(null);
                                }
                            });


                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    if (ready) {
                        editText.requestFocus();
                    }
                    ready = false;
                }
                int len;
                if (lengthLimit instanceof int[]) {
                    try {
                        int[] lengthLimitArr = (int[])lengthLimit;
                        if (i >= lengthLimitArr.length) len = lengthLimitArr[0];
                        else len = lengthLimitArr[i];
                    } catch (Exception e) {
                        len = 1000;
                        e.printStackTrace();
                    }
                }
                else{
                    len = (int) lengthLimit;
                }
                if (editText.getText().toString().length() > len && len != 0) {
                    try {
                        ((TextInputLayout)editText.getParent().getParent()).setError(context.getString(R.string.errorText_fieldValueTooLong));
                        try {
                            editText.addTextChangedListener(new TextChangedListener<EditText>(editText) {
                                @Override
                                public void onTextChanged(EditText target, Editable s) {
                                    ((TextInputLayout)target.getParent().getParent()).setError(null);
                                }
                            });
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    } catch (Exception e) {
                        try {
                            editText.setError(context.getString(R.string.errorText_fieldValueTooLong));
                            editText.addTextChangedListener(new TextChangedListener<EditText>(editText) {
                                @Override
                                public void onTextChanged(EditText target, Editable s) {
                                    target.setError(null);
                                }
                            });
                        } catch (Exception e2) {

                            e2.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    if (ready) {
                        editText.requestFocus();
                    }
                    ready = false;
                }
            }
        }
        return ready;
    }

    public void setErrorToEdittext(EditText editText, String err) {
        try {
            ((TextInputLayout)editText.getParent().getParent()).setError(err);
            try {
                editText.addTextChangedListener(new TextChangedListener<EditText>(editText) {
                    @Override
                    public void onTextChanged(EditText target, Editable s) {
                        ((TextInputLayout)target.getParent().getParent()).setError(null);
                    }
                });
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        } catch (Exception e) {
            try {
                editText.setError(err);
                editText.addTextChangedListener(new TextChangedListener<EditText>(editText) {
                    @Override
                    public void onTextChanged(EditText target, Editable s) {
                        target.setError(null);
                    }
                });
            } catch (Exception e2) {

                e2.printStackTrace();
            }
            e.printStackTrace();
        }

        if (((Activity) context).getCurrentFocus() != null) {
            editText.requestFocus();
        }
    }

    public static void requestFocusWithKeyboard(Context context, EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    public static boolean checkIfEmailIsValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        return email.matches(emailPattern);
    }
}

package com.hichemtech.android_utils;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
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

    public static class Password{
        private static final int MIN_PASSWORD_LENGTH = 6;
        /**
         * free password
         */
        public static final String PASSWORD_LEVEL_1 = "1";

        /**
         * require alpha-numeric password (at least)
         */
        public static final String PASSWORD_LEVEL_2 = "2";

        /**
         * require alpha-numeric password and special characters (at least)
         */
        public static final String PASSWORD_LEVEL_3 = "3";

        public static final int UNKNOWN_ERROR = -1;
        public static final int SUCCESS = 0;
        public static final int ERROR_PASSWORDS_NOT_MATCH = 1;
        public static final int ERROR_PASSWORD_TOO_SHORT = 2;
        public static final int ERROR_PASSWORD_DOES_NOT_RESPECT_CHARACTERS_CONDITIONS = 3;

        private static final String PASSWORD_PATTERN_LEVEL_2 = "^(?=.*\\d)(?=.*[a-z]).*$";
        private static final String PASSWORD_PATTERN_LEVEL_3 = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).*";


        private final String password1;
        private final String password2;

        public Password(String password1, String password2) {
            this.password1 = password1;
            this.password2 = password2;
        }

        public Password(EditText field1, EditText field2) {
            String p1 = "";
            String p2 = "";
            if (field1 != null) {
                p1 = field1.getText().toString();
            }
            if (field2 != null) {
                p2 = field2.getText().toString();
            }
            this.password1 = p1;
            this.password2 = p2;
        }

        public int checkPasswords() {
            return checkPasswords(MIN_PASSWORD_LENGTH, PASSWORD_LEVEL_1);
        }

        public int checkPasswords(String passwordLevel) {
            return checkPasswords(MIN_PASSWORD_LENGTH, passwordLevel);
        }

        public int checkPasswords(int minLength) {
            return checkPasswords(minLength, PASSWORD_LEVEL_1);
        }

        public int checkPasswords(int minLength, String passwordLevel) {
            if (password1 == null || password2 == null) return UNKNOWN_ERROR;
            if (password1.length() < minLength) return ERROR_PASSWORD_TOO_SHORT;
            String patternPassword;
            switch (passwordLevel) {
                case PASSWORD_LEVEL_2:
                    patternPassword = PASSWORD_PATTERN_LEVEL_2;
                    break;
                case PASSWORD_LEVEL_3:
                    patternPassword = PASSWORD_PATTERN_LEVEL_3;
                    break;
                default:
                    patternPassword = null;
                    break;
            }
            if (patternPassword != null) {
                Pattern pattern = Pattern.compile(patternPassword);
                Matcher matcher = pattern.matcher(password1);
                Logger.e("pasw", password1);
                if (!matcher.matches()) return ERROR_PASSWORD_DOES_NOT_RESPECT_CHARACTERS_CONDITIONS;
            }


            if (!password1.equals(password2)) return ERROR_PASSWORDS_NOT_MATCH;

            return SUCCESS;
        }
    }
}

package com.quyum.hyppe.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.quyum.hyppe.net.XLog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

public class StreamUtil {
    public static final synchronized void saveObject(String path, Object saveObject) {
        XLog.e("StreamUtil-saveObject", "-------------time-start:" + System.currentTimeMillis());
        FileOutputStream fOps = null;
        ObjectOutputStream oOps = null;
        File file = new File(path);
        try {
            fOps = new FileOutputStream(file);
            oOps = new ObjectOutputStream(fOps);
            oOps.writeObject(saveObject);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtils.close(oOps);
            CloseUtils.close(fOps);
        }
        XLog.e("StreamUtil-saveObject", "-------------time-end:" + System.currentTimeMillis());
    }

    public static final Object restoreObject(String path) {
        XLog.e("StreamUtil-restore", "-------------time-start:" + System.currentTimeMillis());
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object obj = null;
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtils.close(fis);
            CloseUtils.close(ois);
        }
        XLog.e("StreamUtil-restore", "-------------time-end:" + System.currentTimeMillis());
        return obj;

    }

    public static void save(Context context, String inputText, String name) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = context.openFileOutput(name, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void save(Context context, Bitmap photoBitmap, String name) {
        FileOutputStream out = null;
        try {
            out = context.openFileOutput(name, Context.MODE_PRIVATE);
            photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap load(Context context, String name, String type) {
        FileInputStream in = null;
        try {
            in = context.openFileInput(name);
            return BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String load(Context context, String name) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = context.openFileInput(name);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content.toString();
    }

    static class CloseUtils {
        public static void close(Closeable stream) {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

import java.io.*;

/**
 * @author
 * @date /09/13
 */
public class UserDefineClassLoader extends ClassLoader {
    private String root;

    public UserDefineClassLoader(String root) {
        this.root = root;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = getClassByte(name);
        if (bytes == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, bytes, 0, bytes.length);
        }
    }

    private byte[] getClassByte(String className) {
        String path = classNamePath(className);
        InputStream inputStream = null;
        ByteArrayOutputStream arrayOutputStream=null;
        try {
            inputStream = new FileInputStream(path);
            arrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int readnum = 0;
            while ((readnum = inputStream.read(bytes)) != -1) {
                arrayOutputStream.write(bytes, 0, readnum);
            }
            return arrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String classNamePath(String className) {
        return root + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
    }
}

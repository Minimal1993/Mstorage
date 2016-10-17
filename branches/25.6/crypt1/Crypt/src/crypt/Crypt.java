/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypt;

import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author ilya.gulevskiy
 */
public class Crypt {

    public static void main(String[] args) throws Exception {

        String message = "Как известно, в своем развитии SQL устремился в разные стороны. Крупные производители начали впихивать всякие расширения. И хотя принимаются всякие стандарты (SQL 92), в реальной жизни все крупные БД не поддерживают стандартов полностью + имеют что-то свое. Так вот, SQLite старается жить по принципу «минимальный, но полный набор». Она не поддерживает сложные штуки, но во многом соответствует SQL 92.\n" +
"И вводит некие свои особенности, которые очень удобны, но — не стандартны.";
        String password = "Гондон";
        String code = "uUTg50r2u0Ui/wqF+JA2uzWK1r7GedbF+Ss4aphl6nNfjD7WZTKHHdfDzQAR7aCIigxWHVnol0pE\n" +
"FfMU8K2/wrPLhb5O1IDxU1Y7P7JLuaJH68KL4l7kZVTGK7k7J6KtstGNkLkyNNa1G0yCjlvcLZFM\n" +
"FYviWP39H22Axg5SLP8eu9sqKpojNGFJ9iXnpASw/HL7NfgfVdgdQADUU6V7Aqfm2gND9NyVJqSV\n" +
"CZYb/ne3cqF999tCXhAyjti2H7wt9EOsfwY9MzxTC6+RM8zzgnYH9fz+XU54CTd/JhsiEOvFHB6z\n" +
"igafCT0UCY8zLp0X2BTifMOMrmNlNCSmc5mWwcXu+8jaH7R4/GLA4UkRm/153UDHvaNFEs38uvSH\n" +
"YrB9LYJo3MU1f9aUDF3veXtTH+eDZR62JP+CJ1SOHGVkgFzrfiUxuvp+ANxY/dVSA5FlecmJcjI/\n" +
"MiD1nhq9CFVFwQCOiaiomXom3S9prmffCTM1kC3uTsSi/zQzjQXy/MvAtUHTNJXP8z7TM/dyekFj\n" +
"4HaKgtIibpHXd3kPDQvr2XuUo1obXhICCfCmfKld7p5eQEtWqLRo7QmBaXWZrIooeefPtYxlzZxP\n" +
"SIHOoNo5TsuzWLg9Z0i4cZKAc2pRC8ODLoXo6G/pM+sYJirC6E3KF2z6o7qNIpuQTuz7+SB/Zn5V\n" +
"QUlOm9GtCp0cyda1Hqn2TFd43GmEj2xcWS+EtmwOuyKOO5EXCDrvAPjVCQCSIur7BprI1/5YP9RF\n" +
"dkqiyzj2IfmjtxRX7fK6sLeu5QsPTK6j7a3Qknxjx7cB8JOXQfEFSFXgoZMnERpE+7fv9e6rqJhB\n" +
"pFe4/GiXjIiPL6AU8XsqczEdEHuXuORU/4MCbW7x3GCFzuhfxA+zCxARumd9oZ8xFZl0fGXKM/Bu\n" +
"tWN4ZLlRJiNsJzQqYSVyyCaH5Vlmdb/aPUuv1nnn7SQtaXsGzpOPrZvyu/LeLVQt7/YIQRsBZXnj\n" +
"fKHNStcWrfpREodANdJOYSyU1wYDudRIbEvSNPL9nTzvqNRUGt6O0YkV6fRu8bdmuRDeWXguJ6Sd\n" +
"MGiO7sjgscHdbJlK0zONZn0LWCqDvCD7q2CZ5MQK2XArBBkkh9wR0Y6b/bGEqX9Nslt4yQkXuB0b\n" +
"BY7TnMYXHiiQ";

        AESEncrypter encrypter = new AESEncrypter(password);
        String encrypted = encrypter.encrypt(message);
        String decrypted = encrypter.decrypt(encrypted);
        String decrypted2 = encrypter.decrypt(code);

//        System.out.println("Encrypt(\"" + message + "\", \"" + password + "\") = \"" + encrypted + "\"");
//        System.out.println("Decrypt(\"" + encrypted + "\", \"" + password + "\") = \"" + decrypted + "\"");
        System.out.println( encrypted + "\n" + decrypted2 + "\n");
    }
	
	
	
}

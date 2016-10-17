/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypt;

/**
 *
 * @author bass
 */
public abstract class SimpleAbstract {
    protected String Protected = "fromAbstract";
    public String Public = "publicFromAbstract";
    
    public SimpleAbstract(){
        System.out.println("SimpleAbstract:" + Protected);
        System.out.println("SimpleAbstract:" + Public);
        
        this.methodFromConstructor();
        
        this.methodFromConstructorNotOverrided();
    }
    
    protected void methodFromConstructor(){
        System.out.println("SimpleAbstract.methodFromConstructor:" + Protected);
        System.out.println("SimpleAbstract.methodFromConstructor:" + Public);
    }
    
    protected void methodFromConstructorNotOverrided(){
        System.out.println("SimpleAbstract.methodFromConstructorNotOverrided:" + Protected);
        System.out.println("SimpleAbstract.methodFromConstructorNotOverrided:" + Public);
    }
    
    
}

package com.dly.route_compiler.utils;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import static com.dly.route_compiler.utils.ComplierConstant.BOOLEAN;
import static com.dly.route_compiler.utils.ComplierConstant.BYTE;
import static com.dly.route_compiler.utils.ComplierConstant.DOUBEL;
import static com.dly.route_compiler.utils.ComplierConstant.FLOAT;
import static com.dly.route_compiler.utils.ComplierConstant.INTEGER;
import static com.dly.route_compiler.utils.ComplierConstant.LONG;
import static com.dly.route_compiler.utils.ComplierConstant.SHORT;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.Constants.STRING;

/**
 * Created by dly on 2018/7/23.
 */
public class ParamsTypeUtils {

    /**
     * param TypeMirror
     * <p>
     * 表示java中的类型,包括基本类型,声明类型(类和接口类型),数组类型、类型变量和 null 类型
     * 还可以表示通配符类型参数、executable 的签名和返回类型，以及对应于包和关键字 void 的伪类型。
     * <p>
     * 使用 getKind()来判断类型,
     * 使用 instanceof 确定此建模层次结构中某一对象的有效类 未必 可靠，
     * 因为一个实现可以选择让单个对象实现多个 TypeMirror 子接口。
     */


    public static int paraseType(Element element) {
        TypeMirror typeMirror = element.asType();

        if (typeMirror.getKind().isPrimitive()) {   //判断是否是基本类型 如int,long等,注意Long属于包装类,不属于基本类型
            return typeMirror.getKind().ordinal();
        }

        switch (typeMirror.toString()) {
            case BYTE:
                return ParamTypeKinds.BYTE.ordinal();
            case SHORT:
                return ParamTypeKinds.SHORT.ordinal();
            case INTEGER:
                return ParamTypeKinds.INT.ordinal();
            case LONG:
                return ParamTypeKinds.LONG.ordinal();
            case FLOAT:
                return ParamTypeKinds.FLOAT.ordinal();
            case DOUBEL:
                return ParamTypeKinds.DOUBLE.ordinal();
            case BOOLEAN:
                return ParamTypeKinds.BOOLEAN.ordinal();
            case STRING:
                return ParamTypeKinds.STRING.ordinal();
            default:
                return ParamTypeKinds.OBJECT.ordinal();
        }
    }
}

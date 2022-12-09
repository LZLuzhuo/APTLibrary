/* Copyright 2020 Luzhuo. All rights reserved.
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
package me.luzhuo.lib_compiler.compiler.visitor;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

/**
 * Description:
 *
 * @author Luzhuo
 **/
public final class WechatPayVisitor extends SimpleAnnotationValueVisitor7<Void, Void> {
    // Things to traverse
    private Filer filer = null;
    // type
    private TypeMirror typeMirror = null;
    // package name
    private String ApplicaitonId = null;

    public void setFiler(Filer filer) {
        this.filer = filer;
    }

    @Override
    public Void visitString(String s, Void aVoid) {
        ApplicaitonId = s;
        return aVoid;
    }

    @Override
    public Void visitType(TypeMirror typeMirror, Void aVoid) {
        this.typeMirror = typeMirror;
        generateJavaCode();
        return aVoid;
    }

    /**
     * Generate template code
     */
    private void generateJavaCode(){
        final TypeSpec targetActivity = TypeSpec.classBuilder("WXPayEntryActivity")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.FINAL)
                .superclass(TypeName.get(typeMirror))
                .build();

        final JavaFile javaFile = JavaFile.builder(ApplicaitonId + ".wxapi", targetActivity)
                .addFileComment("This is WeChat pay for Tencent.")
                .build();

        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

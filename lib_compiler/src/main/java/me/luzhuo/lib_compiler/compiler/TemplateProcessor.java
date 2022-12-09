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
package me.luzhuo.lib_compiler.compiler;

import com.google.auto.service.AutoService;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import me.luzhuo.lib_compiler.annotations.WechatLogin;
import me.luzhuo.lib_compiler.annotations.WechatPay;
import me.luzhuo.lib_compiler.compiler.visitor.WechatLoginVisitor;
import me.luzhuo.lib_compiler.compiler.visitor.WechatPayVisitor;

/**
 * Description:
 *
 * @author Luzhuo
 **/
// Use not shown
@SuppressWarnings("unused")
// Generate Meta Information
@AutoService(Processor.class)
public class TemplateProcessor extends AbstractProcessor {

    /**
     * The type of annotation class to be used by the entire program is passed in
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> types = new LinkedHashSet<>();
        final Set<Class<? extends Annotation>> supportAnnotation = getSupportedAnnotations();
        for (Class<? extends Annotation> annotation : supportAnnotation) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations(){
        final Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(WechatLogin.class);
        annotations.add(WechatPay.class);
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        generateWechatLoginCode(roundEnvironment);
        generateWechatPayCode(roundEnvironment);
        return true;
    }

    /**
     * Scan each class to get what we annotated
     */
    private void scan(RoundEnvironment env, Class<? extends Annotation> annotation, AnnotationValueVisitor visitor) {
        for (Element typeElement : env.getElementsAnnotatedWith(annotation)) {
            final List<? extends AnnotationMirror> annotationMirrors = typeElement.getAnnotationMirrors();
            for (AnnotationMirror annotationMirror : annotationMirrors) {
                final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();

                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {
                    entry.getValue().accept(visitor, null);
                }
            }
        }
    }

    /**
     * Generate code files
     */
    private void generateWechatLoginCode(RoundEnvironment env){
        final WechatLoginVisitor visitor = new WechatLoginVisitor();
        visitor.setFiler(processingEnv.getFiler());
        scan(env, WechatLogin.class, visitor);
    }

    private void generateWechatPayCode(RoundEnvironment env){
        final WechatPayVisitor visitor = new WechatPayVisitor();
        visitor.setFiler(processingEnv.getFiler());
        scan(env, WechatPay.class, visitor);
    }
}

package dev.cel.testapp;

import com.google.api.expr.v1alpha1.CheckedExpr;
import com.google.api.expr.v1alpha1.Constant;
import com.google.api.expr.v1alpha1.Expr;
import com.google.api.expr.v1alpha1.SourceInfo;
import dev.cel.common.CelAbstractSyntaxTree;
import dev.cel.common.types.CelTypes;
import dev.cel.common.types.SimpleType;
import dev.cel.runtime.CelRuntime;
import dev.cel.runtime.CelRuntimeFactory;

/** Hello world! */
public class App {
  public static void main(String[] args) throws Exception {
    // Manually constructed checkedExpr.
    // In the real world, this should be produced by a compiler (go, cpp or in Java when made
    // available)
    CheckedExpr checkedExpr =
        CheckedExpr.newBuilder()
            .putTypeMap(1, CelTypes.STRING)
            .setExpr(
                Expr.newBuilder()
                    .setConstExpr(Constant.newBuilder().setStringValue("Hello world!").build())
                    .setId(1)
                    .build())
            .setSourceInfo(
                SourceInfo.newBuilder()
                    .setLocation("<input>")
                    .addLineOffsets(15)
                    .putPositions(1, 0)
                    .build())
            .build();

    CelRuntime celRuntime = CelRuntimeFactory.standardCelRuntimeBuilder().build();
    CelRuntime.Program program =
        celRuntime.createProgram(CelAbstractSyntaxTree.fromCheckedExpr(checkedExpr));

    Object evaluatedResult = program.eval();
    System.out.println(evaluatedResult);
    System.out.println(SimpleType.INT);
  }
}

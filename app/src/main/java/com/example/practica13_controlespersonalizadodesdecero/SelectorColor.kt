package com.example.practica13_controlespersonalizadodesdecero

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.properties.Delegates

class SelectorColor: View {
    //Constructores
    constructor(ctx: Context): super(ctx){}
    constructor(ctx: Context, attrs: AttributeSet): super(ctx, attrs){}
    constructor(ctx: Context, attrs: AttributeSet, defStyleAttr: Int):super(ctx, attrs, defStyleAttr){}

    private var colorSeleccionado = Color.BLACK

    interface OnColorSelectedListener{
        fun onColorSelected(color: Int)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val ancho= calcularAncho(widthMeasureSpec)
        val alto= calcularAlto(heightMeasureSpec)

        //Importante para dimesionar
        setMeasuredDimension(ancho,alto)
    }

    override fun onDraw(canvas: Canvas) {
        //Obtenemos las dimensaiones del control
        val alto=measuredHeight
        val ancho=measuredWidth
        //Colores disponibles
        val pRelleno= Paint()
        pRelleno.style=Paint.Style.FILL
        pRelleno.color= Color.RED
        canvas.drawRect(0f,0f,ancho/4.toFloat(), alto/2.toFloat(),pRelleno)
        pRelleno.color=Color.GREEN
        canvas.drawRect(ancho/4.toFloat(), 0f,2*(ancho/4).toFloat(), alto/2.toFloat(),pRelleno)
        pRelleno.color=Color.BLUE
        canvas.drawRect(2*(ancho/4).toFloat(), 0f, 3*(ancho/4).toFloat(), alto/2.toFloat(),pRelleno)
        pRelleno.color=Color.YELLOW
        canvas.drawRect(3*(ancho/4).toFloat(), 0f, 4*(ancho/4).toFloat(), alto/2.toFloat(),pRelleno)

        //Color Seleccionado
        pRelleno.color=colorSeleccionado
        canvas.drawRect(0F, alto/2.toFloat(),ancho.toFloat(),alto.toFloat(),pRelleno)

        //Marco del control
        val pBorde=Paint()
        pBorde.style=Paint.Style.STROKE
        pBorde.color=Color.WHITE
        canvas.drawRect(0f,0f,ancho-1.toFloat(), alto/2.toFloat(),pBorde)
        canvas.drawRect(0f,0f,ancho-1.toFloat(), alto-1.toFloat(),pBorde)
    }

    private fun calcularAlto(heightMeasureSpec: Int): Int {
        var res=100 //Alto por defecto
        val modo=MeasureSpec.getMode(heightMeasureSpec)
        val limite=MeasureSpec.getSize(heightMeasureSpec)
        if(modo==MeasureSpec.AT_MOST)
            res=limite
        else if(modo == MeasureSpec.EXACTLY)
            res=limite
        return res
    }

    private fun calcularAncho(widthMeasureSpec: Int): Int {
        var res=200 //Ancho por defecto
        val modo=MeasureSpec.getMode(widthMeasureSpec)
        val limite=MeasureSpec.getSize(widthMeasureSpec)
        if(modo==MeasureSpec.AT_MOST)
            res=limite
        else if(modo == MeasureSpec.EXACTLY)
            res=limite
        return res
    }

    private var listener: OnColorSelectedListener?= null

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //Si se presionó en la zona superior
        if (event.y > 0 && event.y < measuredHeight/2){
            //Si se presionó dentro de los límites del control
            if(event.x > 0 && event.x < measuredWidth){
                //Determinamos el color seleccionado según el punto presionado
                colorSeleccionado=
                    if(event.x>measuredWidth/4*3)
                        Color.YELLOW
                    else if(event.x>measuredWidth/4*2)
                        Color.BLUE
                    else if(event.x>measuredWidth/4*1)
                        Color.GREEN
                    else
                        Color.RED
                //Refrescamos el control
                this.invalidate()
            }
        }else if(event.y>measuredWidth/2&&event.y<measuredWidth){
            //Lanzamos el evento externo de seccion de color
            //Listener!!.onColorSelected(colorSeleccionado)
            listener?.onColorSelected(colorSeleccionado)
        }
        return super.onTouchEvent(event)
    }
    fun setOnColorSelectedListener(i: OnColorSelectedListener){
        listener=i
    }
}
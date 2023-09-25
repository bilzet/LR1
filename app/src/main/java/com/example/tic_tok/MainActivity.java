package com.example.tic_tok;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Player representation
    // 0 - X
    // 1 - O
    private LinearLayout board;
    //Кнопки (квадраты) для игры
    private ArrayList<Button> squares = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View.OnClickListener clickListener = (view)->{
            Button button = (Button) view;
            //Если у кнопки есть текст, то выходим из события
            if(!button.getText().toString().equals(""))
                return;
            if(GameInfo.isTurn){
                button.setText(GameInfo.firstSymbol);
                //Вызвать метод проверки победил ли firstSymbol
            }
            else {
                button.setText(GameInfo.secondSymbol);
                //Вызвать метод проврки победил ли secondSymbol
            }
            GameInfo.isTurn = !GameInfo.isTurn;
        };

        View.OnClickListener listener = (view)->{
            Button btn = (Button) view;
            //Если текст из кнопки не пустой то выходим из события
            if(!btn.getText().toString().equals("")) return;
            //Ставим либо X либо O в зависимости от того, кто ходит
            if(GameInfo.isTurn) {
                btn.setText(GameInfo.firstSymbol);
                int [] comb = calcWinnPositions(GameInfo.firstSymbol);
                if(comb != null) Toast.makeText(
                        getApplicationContext(),
                        "winner is "+GameInfo.firstSymbol,
                        Toast.LENGTH_LONG).show();
                for (int i=0; i<comb.length; i++){squares.get(comb[i]).
                setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.purple_200));
                }
            }
            else {
                btn.setText(GameInfo.secondSymbol);
                int [] comb = calcWinnPositions(GameInfo.secondSymbol);
                if(comb != null) Toast.makeText(
                        getApplicationContext(),
                        "winner is "+GameInfo.secondSymbol,
                        Toast.LENGTH_LONG).show();
                for (int i=0; i<comb.length; i++){squares.get(comb[i]).
                        setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.purple_200));
                }
            }
            //Меняем очередность хода true->false
            GameInfo.isTurn = !GameInfo.isTurn;

        };
        board = findViewById(R.id.board);
        generateBoard(3,3,board);
        setListenerToSquares(listener);

    }



    public void generateBoard(int rowCount, int columnCount, LinearLayout board){
        //Генерация строчек от 0 до rowCount
        for(int row = 0; row < rowCount;row++){
            //Создаем контейнер(нашу строку) и вносим ее в board
            LinearLayout rowContainer = generateRow(columnCount);
            board.addView(rowContainer);
        }
    }
    //Устанавливаем слушателя всем кнопкам
    private void setListenerToSquares(View.OnClickListener listener){
        for(int i = 0; i < squares.size();i++)
            //Получаем кнопку из списка и устанавливаем ей слушателя события
            squares.get(i).setOnClickListener(listener);
    }
    //метод генерации строк для board
    private LinearLayout generateRow(int squaresCount){
        //Созданный контейнер (строка) который будет возвращен с кнопками
        LinearLayout rowContainer = new LinearLayout(getApplicationContext());
        rowContainer.setOrientation(LinearLayout.HORIZONTAL);
        //Установка масштабов строки(по длинне контента)
        rowContainer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        for(int square = 0; square < squaresCount;square++){
            //Создаем кнопку для добавления в строку
            Button button = new Button(getApplicationContext());
            //Устанавливаем цвет с помощью tint
            button.setBackgroundTintList(
                    ContextCompat.getColorStateList(
                            getApplicationContext(),
                            R.color.gray_light));
            button.setWidth(convertToDp(50));
            button.setHeight(convertToDp(90));
            rowContainer.addView(button);//Добавляем кнопку в строку
            squares.add(button);//Добавляем кнопку в наш массив
        }
        return rowContainer;
    }
    public int convertToDp(int digit){
        float density = getApplicationContext()
                .getResources().getDisplayMetrics().density;
        return (int)(digit * density + 0.5);
    }
    public int [] calcWinnPositions(String symbol){
        for(int i = 0; i < GameInfo.winCombination.length;i++){
            boolean findComb = true;
            for(int j = 0; j < GameInfo.winCombination[0].length;j++){
                int index = GameInfo.winCombination[i][j];//0, 1, 2
                if (!squares.get(index).getText().toString().equals(symbol)) {
                    //если нет то комбинация не выйгрышная
                    findComb = false;
                    break;
                }
            }
            //если комбинация не поменялась на false то она выйгрышная
            if(findComb) return GameInfo.winCombination[i];
        }
        return null;
    }
}
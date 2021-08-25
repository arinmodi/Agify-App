package com.example.agify


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    fun name_of_day(day : Int) : String {
        var name = "";
        when(day){
            1 -> name = "sun"
            2 -> name = "mon"
            3 -> name = "tus"
            4 -> name = "wed"
            5 -> name = "thu"
            6 -> name = "fri"
            7 -> name = "sat"
            else -> name = "NAN"
        }
        return  name;
    }

    fun datePicker(view : View, year : Int, month : Int, dayOfmonth : Int, min : Boolean){

        // birthday-date-string
        val string = bdd.text.toString() + "-" + bmm.text.toString() + "-" + byy.text.toString();

        // string to date conversion
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val formattedDate = sdf.parse(string)

        // date-to-millisecond conversion
        val millis = formattedDate.time + 86400000

        // current-date-string
        val currentDate = dd.text.toString() + "-" + mm.text.toString() + "-" + yy.text.toString();

        // current-date-formation
        val curruntFomattedDate = sdf.parse(currentDate);

        // date-to-millisecond conversion
        val timeinmills = curruntFomattedDate.time - 86400000



        val datepicker = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { view, syear, smonth, sdayOfMonth ->

                // finding day of week of a selected date
                val simpledateformat = SimpleDateFormat("EEEE")
                val selecteddate = sdf.parse(sdayOfMonth.toString() + "-" + (smonth + 1) .toString() + "-" + syear.toString());
                val dayOfWeek = simpledateformat.format(selecteddate)


                if(min == true){
                    dd.setText(sdayOfMonth.toString())
                    mm.setText((smonth + 1).toString());
                    yy.setText(syear.toString());
                    day_of_week.setText(dayOfWeek.toString());
                }else{
                    bdd.setText(sdayOfMonth.toString())
                    bmm.setText((smonth + 1).toString());
                    byy.setText(syear.toString());
                    bday.setText(dayOfWeek.toString());
                }

            },year,month,dayOfmonth
        );

        if(min == true){
            datepicker.datePicker.minDate = millis;
        }else{
            datepicker.datePicker.maxDate = timeinmills;
        }

        datepicker.show();
    }

    fun calculateAge(currentYear : Int, currentMonth : Int, currentDate : Int, birthdayYear : Int, birthdayMonth : Int, birthdayDate : Int){

        var ageYears = currentYear - birthdayYear;
        var ageMonths = currentMonth - birthdayMonth;
        var ageDays = currentDate - birthdayDate;

        if(ageMonths < 0 ){
            ageYears -= 1;
            ageMonths += 12;
        }

        if(ageDays < 0){
            ageMonths -= 1;
            ageDays += 30;
        }

        numofyears.setText(ageYears.toString());
        numofmonths.setText(ageMonths.toString());
        numofdays.setText(ageDays.toString());

        val nextBirthdayMonths = 11 - ageMonths;
        val nextBirthdayDays = 30 - ageDays;


        nextbirthdaymonths.setText(nextBirthdayMonths.toString());
        nextbirthdaydays.setText(nextBirthdayDays.toString());

        tyears.setText(ageYears.toString());

        val totalmonths = if (ageYears <= 0) ageMonths else ageYears * 12;
        val totalweeks = (totalmonths * 4.345).roundToInt();
        val totaldays = (totalweeks * 7.0).roundToInt();
        val totalhours = (totaldays * 24.0).roundToInt();
        val toatlmins = (totalhours * 60.0).roundToInt();

        tmonths.setText(totalmonths.toString());
        tweeks.setText(totalweeks.toString());
        tdays.setText(totaldays.toString());
        thours.setText(totalhours.toString());
        tmins.setText(toatlmins.toString());
    }

    fun reset(){
        val c = Calendar.getInstance();
        val date = c.get(Calendar.DATE);
        val month = c.get(Calendar.MONTH);
        val year = c.get(Calendar.YEAR);
        val day = c.get(Calendar.DAY_OF_WEEK);


        dd.setText(date.toString());
        mm.setText((month + 1).toString());
        yy.setText(year.toString())
        day_of_week.setText(name_of_day(day));

        bdd.setText("23");
        bmm.setText("06");
        byy.setText("2003")
        bday.setText("MON");

        calculateAge(
            Integer.parseInt(yy.text.toString()),
            Integer.parseInt(mm.text.toString()),
            Integer.parseInt(dd.text.toString()),
            Integer.parseInt(byy.text.toString()),
            Integer.parseInt(bmm.text.toString()) ,
            Integer.parseInt(bdd.text.toString())
        );
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        reset();

        todayDate.setOnClickListener { view ->
            datePicker(view,Integer.parseInt(yy.text.toString()),Integer.parseInt(mm.text.toString()) - 1 ,Integer.parseInt(dd.text.toString()), true)
        }

        bdaycalender.setOnClickListener { view ->
            datePicker(view, Integer.parseInt(byy.text.toString()),Integer.parseInt(bmm.text.toString()) - 1,Integer.parseInt(bdd.text.toString()),false)
        }

        calculate.setOnClickListener { view ->
            calculateAge(
                Integer.parseInt(yy.text.toString()),
                Integer.parseInt(mm.text.toString()),
                Integer.parseInt(dd.text.toString()),
                Integer.parseInt(byy.text.toString()),
                Integer.parseInt(bmm.text.toString()),
                Integer.parseInt(bdd.text.toString())
            );
        };

        clear.setOnClickListener{ view -> reset() }

    }

}

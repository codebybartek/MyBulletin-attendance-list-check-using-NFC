<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class AlterStudentsGroupTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::table('students_groups', function (Blueprint $table) {
            $table->integer('student_id')
                ->unsigned()
                ->nullable();
            $table->integer('group_id')
                ->unsigned()
                ->nullable();
            $table->foreign('student_id')
                ->references('student_id')
                ->on('students');
            $table->foreign('group_id')
                ->references('group_id')
                ->on('groups');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        //
    }
}

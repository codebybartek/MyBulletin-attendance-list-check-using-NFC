<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class AlterTableActivity extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::table('activities', function (Blueprint $table) {
            $table->integer('course_id')
                ->unsigned()
                ->nullable();
            $table->integer('recurrence_id')
                ->unsigned()
                ->nullable();
            $table->integer('professor_id')
                ->unsigned()
                ->nullable();
            $table->integer('group_id')
                ->unsigned()
                ->nullable();
            $table->foreign('course_id')
                ->references('course_id')
                ->on('courses');
            $table->foreign('recurrence_id')
                ->references('recurrence_id')
                ->on('recurrences');
            $table->foreign('professor_id')
                ->references('id')
                ->on('professors');
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

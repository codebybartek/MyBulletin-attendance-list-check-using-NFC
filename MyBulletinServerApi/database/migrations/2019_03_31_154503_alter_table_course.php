<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class AlterTableCourse extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::table('courses', function (Blueprint $table) {
            $table->integer('group_id')
                ->unsigned()
                ->nullable();
            $table->integer('professor_id')
                ->unsigned()
                ->nullable();
            $table->integer('subject_id')
                ->unsigned()
                ->nullable();
            $table->foreign('group_id')
                ->references('group_id')
                ->on('groups');
            $table->foreign('professor_id')
                ->references('id')
                ->on('professors');
            $table->foreign('subject_id')
                ->references('subject_id')
                ->on('subjects');
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

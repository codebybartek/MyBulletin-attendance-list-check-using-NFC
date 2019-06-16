<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class StudentsGroup extends Model
{
    protected $fillable = ['student_id', 'group_id'];
    protected $primaryKey = "student_group_id";
}

<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Course extends Model
{
    protected $fillable = ['name', 'group_id', 'professor_id', 'subject_id'];
    protected $primaryKey = "course_id";

}

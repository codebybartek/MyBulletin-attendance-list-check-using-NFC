<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Student extends Model
{
    protected $fillable = [
        'name', 'email', 'tagId', 'group_id'
    ];
    protected $primaryKey = "student_id";
}

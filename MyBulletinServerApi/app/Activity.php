<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Activity extends Model
{
    protected $fillable = ['title', 'activityDate', 'duration', 'hour', 'course_id', 'recurrence_id', 'checked', 'professor_id', 'group_id'];
    public $primaryKey = 'activity_id';
    protected $casts = [
        'hour' => 'hh:mm:ss'
    ];
}

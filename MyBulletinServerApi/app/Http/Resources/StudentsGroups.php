<?php

namespace App\Http\Resources;

use App\Student;
use Illuminate\Http\Resources\Json\JsonResource;

class StudentsGroups extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        $studentId = $this->student_id;
        $students = Student::all()->where('student_id', $studentId)->first();
        return $students;
    }
}

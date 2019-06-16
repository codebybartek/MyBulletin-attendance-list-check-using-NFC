<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;
use App\Course;

class Subject extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        return [
            'subject_id' => $this->subject_id,
            'name' => $this->name,
            'professor_id' => $this->professor_id,
            'courses' => Course::all()->where('subject_id', $this->subject_id)->toArray()
        ];
    }
}

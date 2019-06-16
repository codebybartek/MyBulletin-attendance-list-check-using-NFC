<?php

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        DB::table('professors')->insert([
            'name' => "admin",
            'email' => "admin@admin.es",
            'password' => bcrypt('admin'),
            'tagId' => "admin",
            'isAdmin' => true
        ]);
    }
}

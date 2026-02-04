import { IsNotEmpty, IsString, IsOptional, Length } from 'class-validator';

export class CreateTodoDto {
    @IsNotEmpty({ message: 'A title is required to create a todo object' })
    @IsString()
    @Length(3,100)
    title: string;

    @IsOptional()
    @IsString()
    @Length(0,100)
    description?: string;
}

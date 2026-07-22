import { Service } from '@angular/core';
import { minLength, PathKind, pattern, required, SchemaPathTree, validate } from '@angular/forms/signals';
import { exclude } from './exclude-special-characters-rules.json'

interface Path {
    name: string;
    email: string;
    password: string;
    againPassword: string;
}

@Service()
export class ValidateRegisterForm {
    private excludeCharacters = exclude;

    public validation(path: SchemaPathTree<Path, PathKind.Root>) { 
        // valida se ambos os campos de senha são iguais
        this.validatePasswordMatch(path);

        // valida se existe número ou caracter especial
        this.checkNameField(path.name);

        // verifica se tem mais de 8 caracteres
        this.checkPasswordLength(path.password);

        // verifica se o campos estão vázios
        this.requiredField(path.name);
        this.requiredField(path.email);
        this.requiredField(path.password);
        this.requiredField(path.againPassword);
    }    

    private checkPasswordLength(password: SchemaPathTree<string, PathKind.Root>) {
        minLength(password, 8, {message: "A senha deve conter mais de 8 caracteres"});
    }

    private checkNameField(name: SchemaPathTree<string, PathKind.Root>) {
        this.excludeCharacters.forEach((specialCharacters) => {
            validate(name, ({value}) => {
                if(value().includes(specialCharacters)){
                    return {
                        kind: "special character",
                        message: `O campo nome não pode conter "${specialCharacters}"`
                    };
                }
                return null;
            }) 
        }) 

        pattern(name, /^[^0-9]+$/, {
            message: "O campo não pode conter número"
        });
    }

    private requiredField(value: SchemaPathTree<string, PathKind.Root>) {
        required(value, {
            message: (context) => {
                let fieldName = context.pathKeys().toString();
                switch(fieldName) {
                    case 'againPassword':
                        fieldName = "Confirmar senha";
                        break;
                    case 'password':
                        fieldName = "senha";
                        break;
                    case 'name':
                        fieldName = "nome";
                        break;
                    case 'email':
                        fieldName = "E-mail";
                        break;
                }
                return `campo ${fieldName} não pode ser vázio`;
            }
        });
    }

    private validatePasswordMatch(path: SchemaPathTree<Path, PathKind.Root>) {
        validate(path.againPassword, ({value, valueOf}) => {
        const password = valueOf(path.password);
        const confirmPassword = value();

        const validatePassword = confirmPassword === password;
        if(!validatePassword) {
            return {
            kind: 'different password',
            message: 'A senha digitada não coincidem.'
            }
        }
        return null;
        })
    }
}
